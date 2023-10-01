package demo.ui.tests;

import demo.ui.dialog.PracticeFormDialog;
import demo.ui.pages.PracticeFormPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;
import javax.annotation.Nonnull;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.sleep;
import static java.util.Locale.US;

@Story("Practice Form")
class PracticeFormTest extends FormsTest {
    private final  String [] subjects = generateRandomSubjects();
    private final  String [] hobbies = generateRandomHobbies();
    private static PracticeFormPage page;

    @Test
    @Tag("readonly")
    @Order(1)
    @Description("Проверка формы")
    @DisplayName("01. Проверка формы")
    void testForm() {
            openStartPage();
            page = selectMenu("Practice Form", PracticeFormPage.class);
            checkValid();
    }

    @Test
    @Tag("readonly")
    @Order(2)
    @Description("Проверка обязательности заполнения полей")
    @DisplayName("02. Проверка обязательности заполнения полей")
    void testMandatoryFields() {
        Allure.step("Находимся на странице 'Text Box'",
             () -> Assumptions.assumeTrue(page != null));
        checkMandatoryFields();
    }

    @Test
    @Tag("readonly")
    @Order(3)
    @Description("Проверка загрузки файла")
    @DisplayName("03. Проверка загрузки файла")
    public void testUpload()  {
        Allure.step("Находимся на странице 'Text Box'",
                () -> Assumptions.assumeTrue(page != null));
        checkUpload();
    }

    @Test
    @Order(4)
    @Description("Проверяем, что в поле Mobile вводиться не больше 10 символов")
    @DisplayName("04. Проверяем, что в поле Mobile вводиться не больше 10 символов")
    public void testFieldMobile () {
        try {
            Allure.step("Находимся на странице 'Text Box'",
                    () -> Assumptions.assumeTrue(page != null));
            checkFieldMobile();
        } finally {
            closeWindow();
        }
    }

    @Test
    @Order(5)
    @Description("Проверка работы формы")
    @DisplayName("05. Проверка работы формы")
    void testWorkForm() {
        try {
            openStartPage();
            page = selectMenu("Practice Form", PracticeFormPage.class);
            checkWork();
        } finally {
            closeWindow();
        }
    }

    private void checkFieldMobile() {
        Allure.step("Заполняем поле 'Mobile' более 10 символами",
                () -> setMobile(getMobile()+"123"));
        Allure.step("Проверяем, что в поле только 10 цифр",
                ()-> page.checkLengthMobile());
    }

    private void checkUpload() {
        Allure.step("Загружаем файл",
                () -> page.uploadFile());
        Allure.step("Проверяем, что файл загрузился",
                () -> page.checkUploadFile());
    }

    private void checkWork() {
        Allure.step("Заполняем поле 'First Name'",
                () -> setFirstName(getFirstName()));
        Allure.step("Пауза для обновления данных",
                () -> sleep(1_000L));
        Allure.step("Заполняем поле 'Last Name'",
                () -> setLastName(getLastName()));
        Allure.step("Пауза для обновления данных",
                () -> sleep(1_000L));
        Allure.step("Заполняем поле 'Email'",
                () -> setEmail(getEmail()));
        Allure.step("Пауза для обновления данных",
                () -> sleep(1_000L));
        Allure.step("Устанавливаем значение 'Male' поле 'Gender'",
                () -> page.setGender());
        Allure.step("Пауза для обновления данных",
                () -> sleep(1_000L));
        Allure.step("Заполняем поле 'Mobile'",
                () -> setMobile(getMobile()));
        Allure.step("Пауза для обновления данных",
                () -> sleep(1_000L));
        Allure.step("Заполняем поле 'Date of Birth'",
                () -> selectDate(getDate()));
        Allure.step("Пауза для обновления данных",
                () -> sleep(1_000L));
        Allure.step("Заполняем поле 'Subjects'",
                () -> setSubjects(subjects));
        Allure.step("Пауза для обновления данных",
                () -> sleep(1_000L));
        Allure.step("Устанавливаем значения в 'Hobbies'",
                () ->setHobbies(hobbies));
        Allure.step("Заполняем поле 'Current Address'",
                () -> setCurrentAddress(getCurrentAddress()));
        Allure.step("Установить в State значение 'NCR'",
                () -> page.selectState(".*NCR.*"));
        Allure.step("Пауза для обновления данных",
                () -> sleep(1_000L));
        Allure.step("Установить в City значение 'Delhi'",
                () -> page.selectCity(".*Delhi.*"));
        Allure.step("Нажимаем на кнопку  'Submit'",
                () -> page.clickSubmit());
        PracticeFormDialog dlg = page(PracticeFormDialog.class);
        Allure.step("Открылось окно  'Thanks for submitting the form'",
                () -> dlg.checkCaption("Thanks for submitting the form"));
        String[] fields = new String[] {
                "Label",
                "Values",
        };
        Allure.step("Проверяем, что табличная часть окна имеет столбцы: " + String.join(", ", fields),
                () -> dlg.checkFields(fields));
        String studentName = getFirstName() + " " + getLastName();
        Allure.step("Проверяем, что строка 'Student Name' имеет значение: " + studentName ,
                () -> dlg.checkValueInString("Student Name", studentName));
        Allure.step("Проверяем, что строка 'Student Email' имеет значение: " + getEmail() ,
                () -> dlg.checkValueInString("Student Email", getEmail()));
        Allure.step("Проверяем, что строка 'Gender' имеет значение: Male ",
                () -> dlg.checkValueInString("Gender", "Male"));
        Allure.step("Проверяем, что строка 'Mobile' имеет значение: " + getMobile(),
                () -> dlg.checkValueInString("Mobile", getMobile()));
        String dateOfBirth = LocalDate.now().minusYears(20).format(DateTimeFormatter.ofPattern("dd MMMM,yyyy").localizedBy(US));
        Allure.step("Проверяем, что строка 'Date of Birth' имеет значение: " + dateOfBirth,
                () -> dlg.checkValueInString("Date of Birth", dateOfBirth));
        Allure.step("Проверяем, что строка 'Subjects' имеет значение: " + String.join(", ", subjects),
                () -> dlg.checkValueInString("Subjects", String.join(", ", subjects)));
        Allure.step("Проверяем, что строка 'Hobbies' имеет значение: " + String.join(", ", hobbies),
                () -> dlg.checkValueInString("Hobbies", String.join(", ", hobbies)));
        Allure.step("Проверяем, что строка 'Picture' пустая ",
                () -> dlg.checkNullInString("Picture"));
        Allure.step("Проверяем, что строка 'Address' имеет значение: " + getCurrentAddress(),
                () -> dlg.checkValueInString("Address", getCurrentAddress()));
        Allure.step("Проверяем, что строка 'State and City' имеет значение: NCR Delhi",
                () -> dlg.checkValueInString("State and City", "NCR Delhi"));
        Allure.step("Проверяем, что кнопка 'Close' активна",
                dlg::checkCloseEnabled);
        Allure.step("Нажимаем кнопку 'Close'",
                dlg::clickCloseButton);
        Allure.step("Проверяем, что диалог закрылся",
                ()-> Assumptions.assumeTrue(dlg.isDisplayed(), "Диалог не закрылся"));
    }

    @Step ("Вводим значение {value} в поле 'Current Address'")
    private void setCurrentAddress (@Nonnull String value) {
        page.setCurrentAddress(value);
    }

    @Step ("Активируем чек-бокс у {hobbies} в разделе 'Hobbies'")
    private void setHobbies (@Nonnull String...hobbies) {
        page.clickCheckboxHobbies(hobbies);
    }

    @Step ("Устанавливаем значение {value} в поле 'Subjects'")
    private void setSubjects (@Nonnull String...value) {
        page.setSubjects(value);
    }

    @Step ("Вводим значение {value} в поле 'Mobile'")
    private void setMobile (@Nonnull String value) {
        page.setMobile(value);
    }

    @Step ("Вводим значение {value} в поле 'Email'")
    public void setEmail (@Nonnull String value) {
        page.setEmail(value);
    }

    @Step ("Вводим значение {value} в поле 'First Name'")
    private void setFirstName (@Nonnull String value) {
        page.setFirstName(value);
    }

    @Step ("Вводим значение {value} в поле 'Last Name'")
    private void setLastName (@Nonnull String value) {
        page.setLastName(value);
    }

    @Step("Ввести дату  {date} в поле 'Date of Birth'")
    private void selectDate(@Nonnull LocalDate date) {
        page.selectDate(date);
    }

    private void checkMandatoryFields() {
        Allure.step("Нажимаем на кнопку  'Submit'",
                () -> page.clickSubmit());
        Allure.step("Пауза для обновления данных",
                () -> sleep(1_000L));
        Allure.step("Проверяем, что поля 'First Name', 'Last Name', 'Mobile Number' выделены красным",
                () -> page.checkMandatoryFields());

    }

    @Step("Проверяем, что экранная форма содержит заголовок, кнопку 'Submit' и поля 'Full Name', 'Email', 'Current Address', 'Permanent Address'")
    private void checkValid() {
        page.checkValid();
    }

    private String @NotNull [] generateRandomHobbies() {
        String[] hobbies = new String[] {
                "Sports",
                "Reading",
                "Music",
        };
        int length = new Random().nextInt(3) + 1;
        String[] array = new String[length];
        HashSet<String> set = new HashSet<String>();
        for (int i = 0; i < length; i++) {
            int index = new Random().nextInt(hobbies.length);
            String hobby = hobbies[index];
            while (set.contains(hobby)) {
                index = new Random().nextInt(hobbies.length);
                hobby = hobbies[index];
            }
            array[i] = hobby;
            set.add(hobby);
        }
        return array;
    }
    private  String @NotNull [] generateRandomSubjects() {
        String[] subjects = new String[]{
                "English",
                "Maths",
                "Physics",
                "Economics",
                "Commerce",
        };
        int length = new Random().nextInt(5) + 1;
        String[] array = new String[length];
        HashSet<String> set = new HashSet<String>();
        for (int i = 0; i < length; i++) {
            int index = new Random().nextInt(subjects.length);
            String hobby = subjects[index];
            while (set.contains(hobby)) {
                index = new Random().nextInt(subjects.length);
                hobby = subjects[index];
            }
            array[i] = hobby;
            set.add(hobby);
        }
        return array;
    }

}
