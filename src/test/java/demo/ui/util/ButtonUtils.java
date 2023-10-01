package demo.ui.util;

import com.codeborne.selenide.SelenideElement;
import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Функции для работы с кнопками.
 */
@UtilityClass
public class ButtonUtils {
    /**
     * Находит кнопку по заголовку.
     *
     * @param caption заголовок
     */
    @Nonnull
    public SelenideElement getByCaption(@Nonnull String caption) {
        return $(byXpath(String.format("//*[text()='%s']/ancestor-or-self::button", caption)));
    }

    /**
     * Находит кнопку по подсказке.
     *
     * @param title подсказка
     */
    @Nonnull
    public SelenideElement getByTitle(@Nonnull String title) {
        return $(byXpath(String.format("//button[@title='%s']", title)));
    }

    /**
     * Находит кнопку по описанию.
     *
     * @param description описание
     */
    @Nonnull
    public SelenideElement getByDescription(@Nonnull String description) {
        return $(byXpath(String.format("//span[text()='%s']/../../div/button", description)));
    }


}
