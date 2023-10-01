package demo.ui.pages;

import com.codeborne.selenide.SelenideElement;
import demo.ui.util.ButtonUtils;
import demo.ui.util.InputUtils;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Страница "Text Box".
 */
public class TextBoxPage implements CheckedPage {
    private static final SelenideElement CAPTION = $(byXpath("//div[@class='main-header' and text()='Text Box']"));
    private static final SelenideElement BORDER = $(byXpath("//div[contains(@class, 'border')]"));
    private static final SelenideElement FULL_NAME_INPUT = InputUtils.getByLabel("Full Name");
    private static final SelenideElement EMAIL_INPUT = InputUtils.getByLabel("Email");
    private static final SelenideElement CURRENT_ADDRESS_INPUT = InputUtils.getByLabelArea("Current Address");
    private static final SelenideElement PERMANENT_ADDRESS_INPUT = InputUtils.getByLabelArea("Permanent Address");
    private static final SelenideElement SUBMIT_BUTTON = ButtonUtils.getByCaption("Submit");

    @Override
    public void checkValid() {
        Stream.of(
                        CAPTION,
                        FULL_NAME_INPUT,
                        EMAIL_INPUT,
                        CURRENT_ADDRESS_INPUT,
                        PERMANENT_ADDRESS_INPUT,
                        SUBMIT_BUTTON)
                .forEach(el -> el.shouldBe(visible));
    }

    /**
     * Проверяем видимость бордера.
     */
    public void checkBorder() {
        BORDER.shouldBe(visible);
    }

    /**
     * Проверяем видимость бордера.
     *
     * @param field название поля.
     * @param value значение поля.
     */
    public void  checkTextInBorder(@Nonnull String field, @Nonnull String value) {
        $(byXpath(String.format("//div[contains(@class, 'border')]/p[contains(text(), '%s')][text()='%s']", field, value)))
                .scrollIntoView(true)
                .shouldBe(visible);
    }

    /**
     * Нажатие на кнопку "Submit".
     */
    public void clickSubmit() {
        SUBMIT_BUTTON.click();
    }

    /**
     * Установка значения в поле "Full Name".
     */
    public void setFullName(@Nonnull String value) {
        InputUtils.setValue(FULL_NAME_INPUT, value);
    }

    /**
     * Установка значения в поле "Email".
     */
    public void setEmail(@Nonnull String value) {
        InputUtils.setValue(EMAIL_INPUT, value);
    }

    /**
     * Установка значения в поле "Current Address".
     */
    public void setCurrentAddress(@Nonnull String value) {
        InputUtils.setValue(CURRENT_ADDRESS_INPUT, value);
    }

    /**
     * Установка значения в поле "Permanent Address".
     */
    public void setPermanentAddress(@Nonnull String value) {
        InputUtils.setValue(PERMANENT_ADDRESS_INPUT, value);
    }

}
