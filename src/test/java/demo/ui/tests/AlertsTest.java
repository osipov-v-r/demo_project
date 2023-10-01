package demo.ui.tests;

import demo.ui.pages.AlertsPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;


@Story("Alerts")
class AlertsTest extends AlertsFrameWindowTest {
    private static AlertsPage page;

    @Test
    @Tag("readonly")
    @Order(1)
    @Description("Проверка формы")
    @DisplayName("01. Проверка формы")
    void testForm() {
        try {
            openStartPage();
            page = selectMenu("Alerts", AlertsPage.class);
            checkValid();
        } finally {
            closeWindow();
        }
    }

    @Test
    @Tag("readonly")
    @Order(2)
    @Description("Проверка стандартного алерта")
    @DisplayName("02. Проверка стандартного алерта")
    void testAlertButton() {
        try {
            openStartPage();
            page = selectMenu("Alerts", AlertsPage.class);
            checkAlertButton();
        } finally {
            closeWindow();
        }
    }

    @Test
    @Tag("readonly")
    @Order(3)
    @Description("Проверка алерта с таймером")
    @DisplayName("03. Проверка алерта с таймером")
    void testTimerAlertButton() {
        try {
            openStartPage();
            page = selectMenu("Alerts", AlertsPage.class);
            checkTimerAlertButton();
        } finally {
            closeWindow();
        }
    }

    @Test
    @Tag("readonly")
    @Order(4)
    @Description("Проверка алерта с подтверждением")
    @DisplayName("04. Проверка алерта с подтверждением")
    void testConfirmButton() {
        try {
            openStartPage();
            page = selectMenu("Alerts", AlertsPage.class);
            checkConfirmButton();
        } finally {
            closeWindow();
        }
    }

    @Test
    @Tag("readonly")
    @Order(5)
    @Description("Проверка алерта с вводом текста")
    @DisplayName("05. Проверка алерта с вводом")
    void testPromptButton() {
        try {
            openStartPage();
            page = selectMenu("Alerts", AlertsPage.class);
            checkPromptButton();
        } finally {
            closeWindow();
        }
    }

    private void checkPromptButton() {
        Allure.step("Нажимаем на кнопку  'Click Me' с описанием 'On button click, prompt box will appear'",
                () -> page.clickPromptButton());
        String message = "Please enter your name";
        Allure.step("Проверяем, что алерт появился и содержит текст: "+message,
                () -> page.checkMessageAlert(message));
        Allure.step("Нажимаем на кнопку 'Отмена' ",
                () -> page.cancelAlert());
        Allure.step("Проверяем, что  подсказка с результатом алерта не появилась",
                () -> page.checkLabelVisibility(false, ""));
        Allure.step("Повторно нажимаем на кнопку  'On button click, prompt box will appear'",
                () -> page.clickPromptButton());
        Allure.step("Проверяем, что алерт появился и содержит текст: "+message,
                () -> page.checkMessageAlert(message));
        Allure.step("Вводим в поле алерта имя: "+ getName(),
                () -> page.sendKeyAlert(getName()));
        Allure.step("Нажимаем на кнопку 'ОК' ",
                () -> page.acceptAlert());
        Allure.step("Проверяем, что  подсказка с результатом алерта появилась и содежит текст "+getName(),
                () -> page.checkLabelVisibility(true, getName()));
    }

    private void checkConfirmButton() {
        Allure.step("Нажимаем на кнопку  'Click Me' с описанием 'On button click, confirm box will appear'",
                () -> page.clickConfirmButton());
        String message = "Do you confirm action?";
        Allure.step("Проверяем, что алерт появился и содержит текст: "+message,
                () -> page.checkMessageAlert(message));
        Allure.step("Нажимаем на кнопку 'Отмена' ",
                () -> page.cancelAlert());
        Allure.step("Проверяем, что появилась подсказка с отменой алерта",
                () -> page.checkMessageAlertResult("Cancel"));
        Allure.step("Повторно нажимаем на кнопку  'Click Me' с описанием 'On button click, confirm box will appear'",
                () -> page.clickConfirmButton());
        Allure.step("Проверяем, что алерт появился и содержит текст: "+message,
                () -> page.checkMessageAlert(message));
        Allure.step("Нажимаем на кнопку 'ОК' ",
                () -> page.acceptAlert());
        Allure.step("Проверяем, что появилась подсказка с подверждения алерта",
                () -> page.checkMessageAlertResult("Ok"));
    }

    private void checkTimerAlertButton() {
        Allure.step("Нажимаем на кнопку  'Click Me' с описанием 'On button click, alert will appear after 5 seconds'",
                () -> page.clickTimerAlertButton());
        Allure.step("Пауза для появление алерта",
                () -> sleep(5_000L));
        String message = "This alert appeared after 5 seconds";
        Allure.step("Проверяем, что алерт появился и содержит текст: "+message,
                () -> page.checkMessageAlert(message));
    }

    private void checkAlertButton() {
        Allure.step("Нажимаем на кнопку  'Click Me' с описанием 'Click Button to see alert'",
                () -> page.clickAlertButton());
        String message = "You clicked a button";
        Allure.step("Проверяем, что алерт появился и содержит текст: "+message,
                () -> page.checkMessageAlert(message));
    }

    @Step("Проверяем, что экранная форма содержит заголовок и кнопки с описанием")
    private void checkValid() {
        page.checkValid();
    }
}
