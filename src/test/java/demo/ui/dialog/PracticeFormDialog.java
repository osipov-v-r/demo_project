package demo.ui.dialog;

import java.util.stream.Stream;
import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Диалог после подтверждения данных на форме "Practice Form".
 */
public class PracticeFormDialog extends ModalDialog {
    /**
     * Проверка столбцов таблицы модального окна.
     *
     * @param fields столбцы
     */
    public void checkFields(@Nonnull String... fields) {
        Stream.of(fields).forEach(field ->
                $(byXpath(String.format("//div[contains(@class, 'modal-content')]//table/thead/tr/th[contains(text(), '%s')]", field)))
                        .scrollIntoView(false)
                        .shouldBe(visible));
    }

    /**
     * Проверка строк таблицы модального окна.
     *
     * @param string название строки
     * @param value значение в строке
     */
    public void checkValueInString(@Nonnull String string, @Nonnull String value) {
        $(byXpath(String.format("//tbody/tr/td[text()='%s']/../td[2]", string)))
                .scrollIntoView(false)
                .shouldBe(visible)
                .shouldHave(text(value));
    }

    /**
     * Проверка, что строка таблицы модального окна пустая.
     *
     * @param string название строки
     */
    public void checkNullInString(@Nonnull String string) {
        $(byXpath(String.format("//tbody/tr/td[text()='%s']/../td[2]", string)))
                .scrollIntoView(false)
                .shouldBe(visible)
                .shouldHave(exactText(""));
    }

}
