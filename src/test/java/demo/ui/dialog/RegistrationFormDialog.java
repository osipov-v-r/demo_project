package demo.ui.dialog;

import com.codeborne.selenide.SelenideElement;
import demo.ui.util.ButtonUtils;
import demo.ui.util.InputUtils;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.visible;

/**
 * Диалог после подтверждения данных на форме "Registration Form".
 */
public class RegistrationFormDialog extends ModalDialog {
    private static final SelenideElement FIRST_NAME_INPUT = InputUtils.getByTitle("First Name");
    private static final SelenideElement LAST_NAME_INPUT = InputUtils.getByTitle("Last Name");
    private static final SelenideElement EMAIL_INPUT = InputUtils.getByLabel("Email");
    private static final SelenideElement AGE_INPUT = InputUtils.getByLabel("Age");
    private static final SelenideElement SALARY_INPUT = InputUtils.getByLabel("Salary");
    private static final SelenideElement DEPARTMENT_INPUT = InputUtils.getByLabel("Department");
    private static final SelenideElement SUBMIT_BUTTON = ButtonUtils.getByCaption("Submit");

    public void checkValid() {
        Stream.of(
                        FIRST_NAME_INPUT,
                        LAST_NAME_INPUT,
                        EMAIL_INPUT,
                        AGE_INPUT,
                        SALARY_INPUT,
                        DEPARTMENT_INPUT,
                        SUBMIT_BUTTON
                )
                .forEach(el -> el.shouldBe(visible));
    }

    /**
     * Установка значения в поле "First Name".
     */
    public String setFirstName(@Nonnull String value) {
        InputUtils.setValue(FIRST_NAME_INPUT, value);
        return value;
    }

    /**
     * Установка значения в поле "Last Name".
     */
    public String setLastName(@Nonnull String value) {
        InputUtils.setValue(LAST_NAME_INPUT, value);
        return value;
    }

    /**
     * Установка значения в поле "Email".
     */
    public String setEmail(@Nonnull String value) {
        InputUtils.setValue(EMAIL_INPUT, value);
        return value;
    }

    /**
     * Установка значения в поле "Age".
     */
    public Integer setAge(int value) {
        InputUtils.setValue(AGE_INPUT, String.valueOf(value));
        return value;
    }

    /**
     * Установка значения в поле "Salary".
     */
    public long setSalary(long value) {
        InputUtils.setValue(SALARY_INPUT, String.valueOf(value));
        return value;
    }

    /**
     * Установка значения в поле "Department".
     */
    public String setDepartment(@Nonnull String value) {
        InputUtils.setValue(DEPARTMENT_INPUT, value);
        return value;
    }

    /**
     * Добавление записи(нажатие кнопки).
     */
    public void clickSubmitButton() {
        SUBMIT_BUTTON.click();
    }

}
