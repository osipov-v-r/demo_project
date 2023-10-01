package demo.ui.tests;

import demo.ui.pages.TextBoxPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import javax.annotation.Nonnull;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Story("Text Box")
class TextBoxTest extends ElementsTest {
    private static TextBoxPage page;

    @Test
    @Tag("readonly")
    @Order(1)
    @Description("Проверка формы")
    @DisplayName("01. Проверка формы")
    void testForm() {
            openStartPage();
            page = selectMenu("Text Box", TextBoxPage.class);
            checkValid();
    }

    @Test
    @Order(2)
    @Description("Проверка работы формы")
    @DisplayName("02. Проверка работы формы")
    void testWorkForm() {
        try {
            Allure.step("Находимся на странице 'Text Box'",
                    () -> Assumptions.assumeTrue(page != null));
            checkWork();
        } finally {
            closeWindow();
        }
    }

    private void checkWork() {
        Allure.step("Заполняем поле 'Full Name'",
                () -> setFullName(getFullName()));
        Allure.step("Заполняем поле 'Email'",
                () -> setEmail(getEmail()));
        Allure.step("Заполняем поле 'Current Address'",
                () -> setCurrentAddress(getCurrentAddress()));
        Allure.step("Заполняем поле в поле 'Permanent Address'",
                () -> setPermanentAddress(getPermanentAddress()));
        Allure.step("Нажимаем на кнопку  'Submit'",
                () -> page.clickSubmit());
        Allure.step("Проверяем, что на странице появилось окно с веденными нами данными ",
                () -> {
                    Allure.step("Проверяем, что окно появилось",
                            () -> page.checkBorder());
                    checkTextInBorder("Name", getFullName());
                    checkTextInBorder("Email", getEmail());
                    checkTextInBorder("Current Address", getCurrentAddress());
                    checkTextInBorder("Permananet Address", getPermanentAddress());
                });
    }

    @Step("Проверяем, что поле '{field}' содержит значение {value}")
    private void checkTextInBorder(String field, String value) {
        page.checkTextInBorder(field, value);
    }

    @Step ("Вводим значение {value} в поле 'Permanent Address'")
    public void setPermanentAddress (@Nonnull String value) {
        page.setPermanentAddress(value);
    }

    @Step ("Вводим значение {value} в поле 'Current Address'")
    public void setCurrentAddress (@Nonnull String value) {
        page.setCurrentAddress(value);
    }

    @Step ("Вводим значение {value} в поле 'Email'")
    public void setEmail (@Nonnull String value) {
        page.setEmail(value);
    }

    @Step ("Вводим значение {value} в поле 'Full Name'")
    public void setFullName (@Nonnull String value) {
        page.setFullName(value);
    }

    @Step("Проверяем, что экранная форма содержит заголовок, кнопку 'Submit' и поля 'Full Name', 'Email', 'Current Address', 'Permanent Address'")
    private void checkValid() {
        page.checkValid();
    }

}
