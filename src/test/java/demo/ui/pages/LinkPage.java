package demo.ui.pages;

import com.codeborne.selenide.SelenideElement;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Страница "Link Page".
 */
public class LinkPage implements CheckedPage {
    private static final SelenideElement CAPTION = $(byXpath("//div[@class='main-header' and text()='Link Page']"));
    private static final SelenideElement LINK = $(byXpath("//a[text()='link']"));

    @Override
    public void checkValid() {
        Stream.of(
                        CAPTION,
                        LINK)
                .forEach(el -> el.shouldBe(visible));
    }

    /**
     * Нажатие на линк.
     */
    public void clickLink() {
        LINK.click();
    }

}
