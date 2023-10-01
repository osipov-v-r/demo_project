package demo.ui.util;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.MatchText;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.or;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Функции для работы с выпадающим списком.
 */
@UtilityClass
public class SelectUtils {
    public enum Options {
        LAST
    }

    /**
     * Находит элемент по метке.
     *
     * @param label метка
     */
    @Nonnull
    public SelenideElement getByLabel(@Nonnull String label, Options... options) {
        SelenideElement el;
        String xpath = String.format("//div[text()='%s']/../..//div[contains(@class, 'indicatorContainer')]", label);
        if (Arrays.binarySearch(options, Options.LAST) >= 0) {
            el = $$(byXpath(xpath)).last();
        } else {
            el = $(byXpath(xpath));
        }
        return el;
    }

    /**
     * Находит элемент по метке.
     *
     * @param labels варианты метки
     */
    @Nonnull
    public SelenideElement getByLabels(@Nonnull String... labels) {
        return $(byXpath(String.format("//legend/span[%s]/ancestor::div/div[contains(@class, 'MuiSelect-select') and @role='button']",
                Arrays.stream(labels).map(label -> String.format("text()='%s'", label)).collect(Collectors.joining(" or ")))));
    }

    /**
     * Проверка доступности редактирования.
     */
    public void checkEnabled(@Nonnull SelenideElement element) {
        element.$(byXpath("following-sibling::input")).shouldBe(enabled);
    }

    /**
     * Проверка недоступности редактирования.
     */
    public void checkDisabled(@Nonnull SelenideElement element) {
        element.$(byXpath("following-sibling::input")).shouldNotBe(visible);
    }

    /**
     * Выбор опции.
     */
    public void select(@Nonnull SelenideElement el, @Nonnull String option) {
        if (!el.getText().equals(option)) {
            el.shouldBe(enabled).click();
            SelenideElement elOption =
                    $$(byXpath("//ul[@role='listbox']/li[@role='option']"))
                            .filter(or("", text(option), matchText(String.format("\\s*%s\\s*", option))))
                            .get(0)
                            .scrollIntoView(true)
                            .shouldBe(visible);
            elOption.click(ClickOptions.usingJavaScript());
            el.shouldHave(text(option));
        }
    }

    /**
     * Возвращает список доступных значений.
     */
    public List<String> getOptions(@Nonnull SelenideElement el) {
        el.shouldBe(enabled).click();
        List<String> options =
                $$(byXpath("//ul[@role='listbox']/li[@role='option']"))
                        .texts()
                        .stream()
                        .filter(StringUtils::isNotBlank)
                        .collect(Collectors.toList());
        $(byXpath("//ul[@role='listbox']")).sendKeys(Keys.ESCAPE);
        return options;
    }

    /**
     * Выбор элемента по индексу (1..N).
     */
    public String selectByIndex(@Nonnull SelenideElement el, int index) {
        el.shouldBe(enabled).click();
        SelenideElement elOption =
                $$(byXpath("//ul[@role='listbox']/li[@role='option']")).get(index - 1)
                        .scrollIntoView(true)
                        .shouldBe(visible);
        String result = elOption.getOwnText();
        elOption.click(ClickOptions.usingJavaScript());
        el.shouldHave(text(result));
        return result.trim();
    }

    /**
     * Выбор элемента по шаблону.
     */
    public String selectByRegex(@Nonnull SelenideElement el, String regex) {
        el.shouldBe(enabled).click();
        SelenideElement elOption = $$(byXpath("//div[text()]"))
                .filter(new MatchText(regex))
                .first()
                .scrollIntoView(true)
                .shouldBe(visible);
        String result = elOption.getOwnText();
        elOption.click(ClickOptions.usingJavaScript());
        return result.trim();
    }
}
