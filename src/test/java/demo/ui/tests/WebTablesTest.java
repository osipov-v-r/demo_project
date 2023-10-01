package demo.ui.tests;

import com.codeborne.selenide.Configuration;
import demo.ui.dialog.RegistrationFormDialog;
import demo.ui.dto.TableRecord;
import demo.ui.pages.WebTablesPage;
import demo.ui.util.TableUtils;
import demo.ui.util.TableUtils.WaitOptions;
import demo.ui.util.WaitUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nonnull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.sleep;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Story("Web Tables")
class WebTablesTest extends ElementsTest {

    private static  long getSalary(boolean action) {
        return action ? 12000 : 22000;
    }
    private static String getDepartment(boolean action) {
        return action ? "Legal" : "Insurance";
    }

    private static WebTablesPage page;
    private static TableRecord tableRecord;

    @Test
    @Tag("readonly")
    @Order(1)
    @Description("Проверка формы")
    @DisplayName("01. Проверка формы")
    void testForm() {
        try {
            openStartPage();
            page = selectMenu("Web Tables", WebTablesPage.class);
            checkValid();
            String[] tableHeader = new String[] {
                    "First Name",
                    "Last Name",
                    "Age",
                    "Email",
                    "Salary",
                    "Department",
                    "Action",
            };
            Allure.step(String.format("Проверяем, что таблица содержит поля: %s",
                            Arrays.toString(tableHeader)),
                    () -> page.checkTableHeader(tableHeader));
        } finally {
            closeWindow();
        }
    }

    @Test
    @Tag("readonly")
    @Order(2)
    @Description("Проверка модального окна 'Registration Form'")
    @DisplayName("02. Проверка модального окна 'Registration Form'")
    void testRegistrationFormDialog() {
        try {
            openStartPage();
            page = selectMenu("Web Tables", WebTablesPage.class);
            openRegistrationFormDialog();
            checkRegistrationFormDialog();
        } finally {
            closeWindow();
        }
    }

    @Test
    @Order(3)
    @Description("Проверка добавления записи в таблицу")
    @DisplayName("03. Проверка добавления записи в таблицу")
    void testAddRecord() {
        openStartPage();
        page = selectMenu("Web Tables", WebTablesPage.class);
        openRegistrationFormDialog();
        tableRecord = addRecord();
    }

    @Test
    @Order(4)
    @Description("Проверка редактирования записи в таблице")
    @DisplayName("04. Проверка редактирования записи в таблице")
    void testEditRecord() {
        Allure.step("Находимся на странице 'Web Tables'",
                () -> Assumptions.assumeTrue(page != null));
        Assumptions.assumeTrue(isNotEmpty(tableRecord), "Запись не была добавлена в таблицу");
        editRecord();
    }

    @Test
    @Order(5)
    @Description("Проверка редактирования записи в таблице")
    @DisplayName("05. Проверка редактирования записи в таблице")
    void testRemoveRecord() {
        Allure.step("Находимся на странице 'Web Tables'",
                () -> Assumptions.assumeTrue(page != null));
        Assumptions.assumeTrue(isNotEmpty(tableRecord), "Запись не была добавлена в таблицу");
        removeRecord();
    }

    private void removeRecord() {
        Allure.step("Нажать кнопку 'Удалить' у записи в таблице" ,
                () -> page.clickDelete(tableRecord));
        Allure.step("Пауза для обновления данных", () -> sleep(500));
        Allure.step("Проверить таблицу после редактирования записи о зачистке",
                () -> checkNotExists(tableRecord));
    }

    private void checkNotExists(@Nonnull TableRecord tableRecord) {
        AtomicBoolean result = new AtomicBoolean();
        WaitUtils.waitUntil(result::get,
                () -> {
                    Allure.step("Пауза для обновления данных", () -> sleep(2_000));
                    List<TableRecord> data = new ArrayList<>();
                    Allure.step("Получены данные из таблицы",
                            () -> data.addAll(page.getData(2_000, TableUtils.WaitOptions.ALLOW_EMPTY)));
                    result.set(!data.contains(tableRecord));
                },
                100,
                Configuration.timeout,
                "В таблице нашли запись " + tableRecord);
    }

    private void  editRecord() {
        Allure.step("Редактируем созданную запись", () -> {
            Allure.step("Нажимаем на кнопку 'Редактировать' у созданной записи",
                    () -> page.clickEdit(tableRecord));
            RegistrationFormDialog dlg = page(RegistrationFormDialog.class);
            Allure.step("Ввести в поле 'Salary' значение: " + getSalary(false),
                    () -> tableRecord.setSalary(dlg.setSalary(getSalary(false))));
            Allure.step("Ввести в поле 'Department' значение: " + getDepartment(false),
                    () -> tableRecord.setDepartment(dlg.setDepartment(getDepartment(false))));
            Allure.step("Нажать кнопку 'Сохранить'", dlg::clickSubmitButton);
            Allure.step("Пауза для обновления данных", () -> sleep(500));
            Allure.step("Проверяем, что диалог закрылся",
                    ()-> Assertions.assertFalse(dlg.isDisplayed(), "Диалог не закрылся"));
            Allure.step("Проверить таблицу после редактирования записи",
                    () -> checkExists(tableRecord));
        });
    }

    private TableRecord addRecord() {
        RegistrationFormDialog dlg = page(RegistrationFormDialog.class);
        TableRecord tableRecord = TableRecord.builder().build();
        Allure.step("Создаем новую запись в таблице", () -> {
            Allure.step("Ввести в поле 'First Name' значение: " + getFirstName(),
                    () -> tableRecord.setFirstName(dlg.setFirstName(getFirstName())));
            Allure.step("Ввести в поле 'First Name' значение: " + getLastName(),
                    () -> tableRecord.setLastName(dlg.setLastName(getLastName())));
            Allure.step("Ввести в поле 'Email' значение: " + getEmail(),
                    () -> tableRecord.setEmail(dlg.setEmail(getEmail())));
            Allure.step("Ввести в поле 'Age' значение: " + age().toString(),
                    () -> tableRecord.setAge(dlg.setAge(age())));
            Allure.step("Ввести в поле 'Salary' значение: " + getSalary(true),
                    () -> tableRecord.setSalary(dlg.setSalary(getSalary(true))));
            Allure.step("Ввести в поле 'Department' значение: " + getDepartment(true),
                    () -> tableRecord.setDepartment(dlg.setDepartment(getDepartment(true))));
            Allure.step("Нажать кнопку 'Сохранить'", dlg::clickSubmitButton);
            Allure.step("Пауза для обновления данных", () -> sleep(500));
            Allure.step("Проверяем, что диалог закрылся",
                    ()-> Assertions.assertFalse(dlg.isDisplayed(), "Диалог не закрылся"));
            Allure.step("Проверить таблицу после добавления записи о зачистке",
                    () -> checkExists(tableRecord));
        });
        return tableRecord;
    }

    private void checkExists(@Nonnull TableRecord tableRecord) {
        AtomicBoolean found = new AtomicBoolean();
        WaitUtils.waitUntil(found::get,
                () -> {
                    List<TableRecord> data = new ArrayList<>();
                    Allure.step("Пауза для обновления данных", () -> sleep(1_000));
                    Allure.step("Получены данные из таблицы",
                            () -> data.addAll(page.getData(3_000, WaitOptions.ALLOW_EMPTY)));
                    found.set(data.contains(tableRecord));
                },
                100,
                Configuration.timeout,
                "В таблице нет записи: "+tableRecord);
    }

    public void checkRegistrationFormDialog() {
        RegistrationFormDialog dlg = page(RegistrationFormDialog.class);
        Allure.step("Открылось окно  'Registration Form'",
                () -> dlg.checkCaption("Registration Form"));
        Allure.step("Проверяем, что диалог содержит поля 'First Name', 'Last Name', 'Age', 'Email', 'Salary', 'Department' и кнопку 'Submit'",
                dlg::checkValid);
    }

    @Step("Открываем модальное окно 'Registration Form'")
    public void openRegistrationFormDialog() {
        Allure.step("Нажимаем на кнопку 'Add'",
                () -> page.clickAddButton());
    }

    @Step("Проверяем, что экранная форма содержит заголовок, кнопку 'Add' и поле 'Type to search' для поиска")
    private void checkValid() {
        page.checkValid();
    }

}
