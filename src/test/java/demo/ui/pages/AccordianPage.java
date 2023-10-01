package demo.ui.pages;

import com.codeborne.selenide.SelenideElement;
import demo.ui.util.AccordianUtils;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class AccordianPage implements CheckedPage{
    private static final SelenideElement CAPTION = $(byXpath("//div[@class='main-header' and text()='Accordian']"));
    private static final SelenideElement ACCORDIAN_CHART1 = AccordianUtils.getByCaption("What is Lorem Ipsum?");
    private static final SelenideElement ACCORDIAN_CHART2 = AccordianUtils.getByCaption("Where does it come from?");
    private static final SelenideElement ACCORDIAN_CHART3 = AccordianUtils.getByCaption("Why do we use it?");

    @Override
    public void checkValid() {
        Stream.of(
                        CAPTION,
                        ACCORDIAN_CHART1,
                        ACCORDIAN_CHART2,
                        ACCORDIAN_CHART3)
                .forEach(el -> el.shouldBe(visible));
    }

    /**
     * Нажатие на раздел "What is Lorem Ipsum?".
     */
    public void clickAccordian1() {
        ACCORDIAN_CHART1.click();
    }

    /**
     * Нажатие на раздел "Where does it come from?".
     */
    public void clickAccordian2() {
        ACCORDIAN_CHART2.click();
    }

    /**
     * Нажатие на раздел "Why do we use it?".
     */
    public void clickAccordian3() {
        ACCORDIAN_CHART3.click();
    }

    /**
     * Проверка текста под открытым разделом.
     */
    public void checkTextInCard(int chart, @Nonnull String caption) {
        $$(byXpath("//div[@class='card-body']")).get(chart).shouldBe(visible).shouldHave(text(caption));
    }
}
