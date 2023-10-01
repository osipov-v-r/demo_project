package demo.ui.util;

import com.codeborne.selenide.SelenideElement;
import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.editable;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Функции для работы с полем ввода.
 */
@UtilityClass
public class InputUtils {
    /**
     * Находит поле ввода по метке.
     *
     * @param label метка
     */
    @Nonnull
    public SelenideElement getByLabel(@Nonnull String label) {
        return $(byXpath(String.format("//div/label[text()='%s']/../..//div/input", label)));
    }

    /**
     * Находит область ввода по метке.
     *
     * @param label метка
     */
    @Nonnull
    public SelenideElement getByLabelArea(@Nonnull String label) {
        return $(byXpath(String.format("//div/label[text()='%s']/../..//div/textarea", label)));
    }

    /**
     * Находит поле ввода по подсказке.
     *
     * @param label подсказка
     */
    @Nonnull
    public SelenideElement getByTitle(@Nonnull String label) {
        return $(byXpath(String.format("//input[@placeholder='%s']", label)));
    }

    /**
     * Установка значения.
     *
     * @param input элемент
     * @param value значение
     */
    public void setValue(@Nonnull SelenideElement input, @Nonnull String value) {
        clear(input);
        input.setValue(value);
    }

    /**
     * Очистка значения.
     *
     * @param input элемент
     */
    public void clear(@Nonnull SelenideElement input) {
        input.shouldBe(editable).sendKeys(Keys.CONTROL + "A", Keys.BACK_SPACE);
    }

}
