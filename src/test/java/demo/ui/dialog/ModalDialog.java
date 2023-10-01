package demo.ui.dialog;

import com.codeborne.selenide.SelenideElement;
import demo.ui.util.ButtonUtils;
import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Модальный диалог, общие функции.
 */
public class ModalDialog {
    static final SelenideElement CLOSE_BUTTON = ButtonUtils.getByCaption("Close");

    /**
     * Проверка отображения.
     */
    public boolean isDisplayed() {
        return $(byXpath("//div[contains(@class, 'modal-dialog')]//div[contains(@class, 'modal-content') "
                + "or contains(@class, 'MuiDialogContent-root')]"))
                .isDisplayed();
    }

    /**
     * Проверка заголовка.
     */
    public void checkCaption(@Nonnull String caption) {
        $(byXpath(String.format("//div[contains(@class, 'modal-dialog')]//div[contains(@class, 'modal-content') "
                + "or contains(@class, 'MuiDialogContent-root')]//div[text()='%s']", caption)))
                .shouldBe(visible);
    }

    /**
     * Проверка описания.
     */
    public void checkDescription(@Nonnull String caption) {
        $(byXpath("//div[@class='modal-body']")).shouldHave(text(caption));
    }

    /**
     * Проверка доступности закрытия (нажатие кнопки).
     */
    public void checkCloseEnabled() {
        CLOSE_BUTTON.shouldBe(enabled);
    }

    /**
     * Закрытие диалога (нажатие кнопки).
     */
    public void clickCloseButton() {
        CLOSE_BUTTON.click();
    }

}
