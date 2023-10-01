package demo.ui.tests;

import demo.ui.pages.BrowserWindowsPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;

@Story("Browser Windows")
class BrowserWindowsTest extends AlertsFrameWindowTest {
    private static BrowserWindowsPage page;

    @Test
    @Tag("readonly")
    @Order(1)
    @Description("Проверка формы")
    @DisplayName("01. Проверка формы")
    void testForm() {
        try {
            openStartPage();
            page = selectMenu("Browser Windows", BrowserWindowsPage.class);
            checkValid();
        } finally {
            closeWindow();
        }
    }

    @Test
    @Tag("readonly")
    @Order(2)
    @Description("Проверка кнопки 'New Tab'")
    @DisplayName("02. Проверка кнопки 'New Tab'")
    void testNewTabButton() {
        try {
            openStartPage();
            page = selectMenu("Browser Windows", BrowserWindowsPage.class);
            checkNewTabButton();
        } finally {
            closeWindow();
        }
    }

    @Test
    @Tag("readonly")
    @Order(3)
    @Description("Проверка кнопки 'New Window'")
    @DisplayName("03. Проверка кнопки 'New Window'")
    void testNewWindowButton() {
        try {
            openStartPage();
            page = selectMenu("Browser Windows", BrowserWindowsPage.class);
            checkNewWindowButton();
        } finally {
            closeWindow();
        }
    }

    @Test
    @Tag("readonly")
    @Order(4)
    @Description("Проверка кнопки 'New Window Message'")
    @DisplayName("04. Проверка кнопки 'New Window Message'")
    void testNewWindowMessageButton() {
        try {
            openStartPage();
            page = selectMenu("Browser Windows", BrowserWindowsPage.class);
            checkNewWindowMessageButton();
        } finally {
            closeWindow();
        }
    }

    private void checkNewWindowMessageButton() {
        Allure.step("Нажимаем на кнопку  'New Window Message'",
                () -> page.clickNewWindowMessage());
        Allure.step("Пауза для обновления данных",
                () -> sleep(1_000L));
        Allure.step("Проверяем, что открылось новое окно",
                ()-> page.checkNewMessage());
    }

    private void checkNewWindowButton() {
        Allure.step("Нажимаем на кнопку  'New Window'",
                () -> page.clickNewWindow());
        Allure.step("Пауза для обновления данных",
                () -> sleep(1_000L));
        Allure.step("Проверяем, что открылось новое окно",
                ()-> page.checkNewTabOrWindow());
    }

    private void checkNewTabButton() {
        Allure.step("Нажимаем на кнопку  'New Tab'",
                () -> page.clickNewTab());
        Allure.step("Пауза для обновления данных",
                () -> sleep(1_000L));
        Allure.step("Проверяем, что открылась новая вкладка в браузере",
                ()-> page.checkNewTabOrWindow());
    }

    @Step("Проверяем, что экранная форма содержит заголовок, кнопки 'New Tab', 'New Window', 'New Window Message'")
    private void checkValid() {
        page.checkValid();
    }
}
