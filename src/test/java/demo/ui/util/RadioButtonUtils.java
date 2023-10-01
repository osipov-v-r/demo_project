package demo.ui.util;

import com.codeborne.selenide.SelenideElement;
import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Функции для работы с радио-баттонами.
 */
@UtilityClass
public class RadioButtonUtils {
    /**
     * Находит баттон по метке.
     *
     * @param label метка
     */
    @Nonnull
    public SelenideElement getByLabel(@Nonnull String label) {
        return $(byXpath(String.format("//input[@type='radio']/../label[text()='%s']", label)));
    }

    /**
     * Установка значения.
     *
     * @param radioButton элемент
     */
    public void setValue(@Nonnull SelenideElement radioButton) {
        radioButton.click();
    }
}
