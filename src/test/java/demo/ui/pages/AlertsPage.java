package demo.ui.pages;

import com.codeborne.selenide.SelenideElement;
import demo.ui.util.ButtonUtils;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

/**
 * Страница "Browser Windows".
 */
public class AlertsPage implements CheckedPage{
    private static final SelenideElement CAPTION = $(byXpath("//div[@class='main-header' and text()='Alerts']"));
    private static final SelenideElement ALERT_BUTTON = ButtonUtils.getByDescription("Click Button to see alert ");
    private static final SelenideElement TIMER_ALERT_BUTTON = ButtonUtils.getByDescription("On button click, alert will appear after 5 seconds ");
    private static final SelenideElement CONFIRM_BUTTON = ButtonUtils.getByDescription("On button click, confirm box will appear");
    private static final SelenideElement PROMPT_BUTTON = ButtonUtils.getByDescription("On button click, prompt box will appear");

    @Override
    public void checkValid() {
        Stream.of(
                        CAPTION,
                        ALERT_BUTTON,
                        TIMER_ALERT_BUTTON,
                        CONFIRM_BUTTON,
                        PROMPT_BUTTON)
                .forEach(el -> el.shouldBe(visible));
    }

    /**
     * Нажатие на кнопку "PROMPT_BUTTON".
     */
    public void clickPromptButton() {
        PROMPT_BUTTON.click();
    }

    /**
     * Нажатие на кнопку "CONFIRM_BUTTON".
     */
    public void clickConfirmButton() {
        CONFIRM_BUTTON.click();
    }

    /**
     * Нажатие на кнопку "TIMER_ALERT_BUTTON".
     */
    public void clickTimerAlertButton() {
        TIMER_ALERT_BUTTON.click();
    }

    /**
     * Нажатие на кнопку "ALERT_BUTTON".
     */
    public void clickAlertButton() {
        ALERT_BUTTON.click();
    }

    /**
     * Проверка текста в алерте.
     */
    public void checkMessageAlert(@Nonnull String message) {
        Assertions.assertEquals(message, switchTo().alert().getText());
    }

    /**
     * Отмена алерта.
     */
    public void cancelAlert() {
        switchTo().alert().dismiss();
    }

    /**
     * Подтверждение алерта.
     */
    public void acceptAlert() {
        switchTo().alert().accept();
    }

    /**
     * Ввод текста в алерте.
     */
    public void sendKeyAlert(@Nonnull String name) {
        switchTo().alert().sendKeys(name);
    }

    /**
     * Проверка сообщение с результатом алерта.
     */
    public void checkMessageAlertResult(@Nonnull String message) {
        $(byXpath(String.format("//span[@id='confirmResult' and text()='%s']", message))).shouldHave(visible);
    }

    /**
     * Проверка наличие сообщение с результатом алерта и его текста.3
     */
    public void checkLabelVisibility(boolean isVisible, String name) {
        if (isVisible) {
            $(byXpath("//span[@id='promptResult']")).shouldBe(visible).shouldHave(text(name));
        } else {
            $(byXpath("//span[@id='promptResult']")).shouldNotBe(visible);
        }
    }


}
