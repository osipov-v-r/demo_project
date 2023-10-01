package demo.ui.util;

import com.codeborne.selenide.SelenideElement;
import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Функции для работы с чек-боксом.
 */
@UtilityClass
public class CheckBoxUtils {
    /**
     * Находит чек-бокс по метке.
     *
     * @param label метка
     */
    @Nonnull
    public SelenideElement getByLabel(@Nonnull String label) {
        return $(byXpath(String.format("//label[text()='%s']/..//input[@type='checkbox']", label)));
    }

}
