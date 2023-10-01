package demo.ui.tests;

import demo.ui.dialog.ModalDialog;
import demo.ui.pages.ModalDialogsPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.sleep;

@Story("Modal Dialogs")
class ModalDialogsTest extends AlertsFrameWindowTest {
    private static ModalDialogsPage page;

    @Test
    @Tag("readonly")
    @Order(1)
    @Description("Проверка формы")
    @DisplayName("01. Проверка формы")
    void testForm() {
        try {
            openStartPage();
            page = selectMenu("Modal Dialogs", ModalDialogsPage.class);
            checkValid();
        } finally {
            closeWindow();
        }
    }

    @Test
    @Tag("readonly")
    @Order(2)
    @Description("Проверка 'Small modal'")
    @DisplayName("02. Проверка 'Small modal'")
    void testSmallDialog() {
        openStartPage();
        page = selectMenu("Modal Dialogs", ModalDialogsPage.class);
        checkSmallDialog();
    }

    @Test
    @Tag("readonly")
    @Order(3)
    @Description("Проверка 'Large modal'")
    @DisplayName("03. Проверка 'Large modal'")
    void testLargeDialog() {
        try {
            Allure.step("Находимся на странице 'Modal Dialogs'",
                    () -> Assumptions.assumeTrue(page != null));
            checkLargeDialog();
        } finally {
            closeWindow();
        }
    }

    private void checkLargeDialog() {
        Allure.step("Нажимаем на кнопку  'Large modal'",
                () -> page.clickLargeModal());
        ModalDialog dlg = page(ModalDialog.class);
        Allure.step("Открылось окно  'Large Modal'",
                () -> dlg.checkCaption("Large Modal"));
        String caption = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been "
                + "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type "
                + "and scrambled it to make a type specimen book. It has survived not only five centuries, but also the "
                + "leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with"
                + " the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing"
                + " software like Aldus PageMaker including versions of Lorem Ipsum.";
        Allure.step("Проверяем, что окно содержит описание: "+caption,
                () -> dlg.checkDescription(caption));
        Allure.step("Проверяем, что кнопка 'Close' активна ",
                dlg::checkCloseEnabled);
        Allure.step("Нажимаем на кнопку 'Close'",
                dlg::clickCloseButton);
        Allure.step("Пауза для закрытия окна", () -> sleep(500));
        Allure.step("Проверяем, что диалог закрылся",
                ()-> Assertions.assertFalse(dlg.isDisplayed(), "Диалог не закрылся"));
    }

    private void checkSmallDialog() {
        Allure.step("Нажимаем на кнопку  'Small modal'",
                () -> page.clickSmallModal());
        ModalDialog dlg = page(ModalDialog.class);
        Allure.step("Открылось окно  'Small Modal'",
                () -> dlg.checkCaption("Small Modal"));
        String caption = "This is a small modal. It has very less content";
        Allure.step("Проверяем, что окно содержит описание: "+caption,
                () -> dlg.checkDescription(caption));
        Allure.step("Проверяем, что кнопка 'Close' активна ",
                dlg::checkCloseEnabled);
        Allure.step("Нажимаем на кнопку 'Close'",
                dlg::clickCloseButton);
        Allure.step("Пауза для закрытия окна", () -> sleep(500));
        Allure.step("Проверяем, что диалог закрылся",
                ()-> Assertions.assertFalse(dlg.isDisplayed(), "Диалог не закрылся"));
    }

    @Step("Проверяем, что экранная форма содержит заголовок и кнопки 'Small modal', 'Large modal'")
    private void checkValid() {
        page.checkValid();
    }

}
