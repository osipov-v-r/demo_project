package demo.ui.pages;

import com.codeborne.selenide.SelenideElement;
import demo.ui.util.ButtonUtils;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Страница "Modal Dialogs".
 */
public class ModalDialogsPage implements CheckedPage {
    private static final SelenideElement CAPTION = $(byXpath("//div[@class='main-header' and text()='Modal Dialogs']"));
    private static final SelenideElement SMALL_MODAL_BUTTON = ButtonUtils.getByCaption("Small modal");
    private static final SelenideElement LARGE_MODAL_BUTTON = ButtonUtils.getByCaption("Large modal");

    @Override
    public void checkValid() {
        Stream.of(
                        CAPTION,
                        SMALL_MODAL_BUTTON,
                        LARGE_MODAL_BUTTON)
                .forEach(el -> el.shouldBe(visible));
    }

    /**
     * Нажатие на кнопку "Small modal".
     */
    public void clickSmallModal() {
        SMALL_MODAL_BUTTON.click();
    }

    /**
     * Нажатие на кнопку "Large modal".
     */
    public void clickLargeModal() {
        LARGE_MODAL_BUTTON.click();
    }

}
