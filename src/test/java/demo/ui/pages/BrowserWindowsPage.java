package demo.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import demo.ui.util.ButtonUtils;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Страница "Browser Windows".
 */
public class BrowserWindowsPage implements CheckedPage{
    private static final SelenideElement CAPTION = $(byXpath("//div[@class='main-header' and text()='Browser Windows']"));
    private static final SelenideElement NEW_TAB_BUTTON = ButtonUtils.getByCaption("New Tab");
    private static final SelenideElement NEW_WINDOW_BUTTON = ButtonUtils.getByCaption("New Window");
    private static final SelenideElement NEW_WINDOW_MESSAGE_BUTTON = ButtonUtils.getByCaption("New Window Message");

    @Override
    public void checkValid() {
        Stream.of(
                CAPTION,
                NEW_TAB_BUTTON,
                NEW_WINDOW_BUTTON,
                NEW_WINDOW_MESSAGE_BUTTON)
                .forEach(el -> el.shouldBe(visible));
    }

    /**
     * Нажатие на кнопку "New Tab".
     */
    public void clickNewTab() {
        NEW_TAB_BUTTON.click();
    }

    /**
     * Нажатие на кнопку "New Window".
     */
    public void clickNewWindow() {
        NEW_WINDOW_BUTTON.click();
    }

    /**
     * Нажатие на кнопку "New Window Message".
     */
    public void clickNewWindowMessage() {
        NEW_WINDOW_MESSAGE_BUTTON.click();
    }

    /**
     * Проверка открытия новой вкладки/окна.
     */
    public void checkNewTabOrWindow() {
        if (WebDriverRunner.getWebDriver().getWindowHandles().size() != 2) {
            fail("Новая вкладка не открылась");
        }
        switchTo().window(1);
        $(byXpath("//h1[text()='Thread QA Sample']")).shouldBe(visible);
    }

    /**
     * Проверка открытия сообщение.
     */
    public void checkNewMessage() {
        if (WebDriverRunner.getWebDriver().getWindowHandles().size() != 2) {
            fail("Новая вкладка не открылась");
        }
    }
}
