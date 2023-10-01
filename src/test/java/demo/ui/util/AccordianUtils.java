package demo.ui.util;

import com.codeborne.selenide.SelenideElement;
import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Функции для работы с аккордеоном.
 */
@UtilityClass
public class AccordianUtils {
    /**
     * Находит раздел по заголовку.
     *
     * @param caption заголовок
     */
    @Nonnull
    public SelenideElement getByCaption(@Nonnull String caption) {
        return $(byXpath(String.format("//div[@class='accordion']//div[@class='card-header' and text()='%s']", caption)));
    }
}
