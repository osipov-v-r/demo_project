package demo.ui.pages;

import com.codeborne.selenide.SelenideElement;
import demo.ui.util.ButtonUtils;
import demo.ui.util.DatePickerUtils;
import demo.ui.util.InputUtils;
import demo.ui.util.RadioButtonUtils;
import demo.ui.util.SelectUtils;
import java.io.File;
import java.time.LocalDate;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Страница "Practice Form".
 */
public class PracticeFormPage implements CheckedPage{
    private static final SelenideElement CAPTION = $(byXpath("//div[@class='main-header' and text()='Practice Form']"));
    private static final SelenideElement FIRST_NAME_INPUT = InputUtils.getByTitle("First Name");
    private static final SelenideElement LAST_NAME_INPUT = InputUtils.getByTitle("Last Name");
    private static final SelenideElement EMAIL_INPUT = InputUtils.getByLabel("Email");
    private static final SelenideElement MALE_RADIO_BUTTON = RadioButtonUtils.getByLabel("Male");
    private static final SelenideElement SELECT_PICTURE_BUTTON = $(byXpath("//*[@id='uploadPicture']"));
    private static final SelenideElement DATE_PICKER = $("[class='react-datepicker__input-container']");
    private static final SelenideElement MOBILE_INPUT = InputUtils.getByTitle("Mobile Number");
    private static final SelenideElement SUBJECTS_INPUT = InputUtils.getByLabel("Subjects");
    private static final SelenideElement STATE_SELECT = SelectUtils.getByLabel("Select State");
    private static final SelenideElement CITY_SELECT = SelectUtils.getByLabel("Select City");
    private static final SelenideElement CURRENT_ADDRESS_INPUT = InputUtils.getByLabelArea("Current Address");
    private static final SelenideElement SUBMIT_BUTTON = ButtonUtils.getByCaption("Submit");

    @Override
    public void checkValid() {
        Stream.of(
                        CAPTION,
                        FIRST_NAME_INPUT,
                        LAST_NAME_INPUT,
                        EMAIL_INPUT,
                        DATE_PICKER,
                        MOBILE_INPUT,
                        CURRENT_ADDRESS_INPUT,
                        SUBJECTS_INPUT,
                        STATE_SELECT,
                        CITY_SELECT,
                        SUBMIT_BUTTON)
                .forEach(el -> el.shouldBe(visible));
    }

    /**
     * Выбор хобби.
     * @param hobbies хобби
     */
    public void clickCheckboxHobbies(@Nonnull String... hobbies) {
        Stream.of(hobbies).forEach(hobby ->
                $(byXpath(String.format("//input[@type='checkbox']/..//label[text()='%s']", hobby)))
                        .scrollIntoView(false)
                        .click());
    }

    /**
     * Установка значения в поле "Subjects".
     */
    public void setSubjects(@Nonnull String...values) {
        Stream.of(values).forEach(value -> SUBJECTS_INPUT.setValue(value).pressEnter());

    }

    /**
     * Выбор State по строке названия.
     *
     * @param regex шаблон, по которому ищем запись названия
     */
    public String selectState(@Nonnull String regex) {
        return SelectUtils.selectByRegex(STATE_SELECT, regex);
    }

    /**
     * Выбор City по строке названия.
     *
     * @param regex шаблон, по которому ищем запись названия
     */
    public String selectCity(@Nonnull String regex) {
        return SelectUtils.selectByRegex(CITY_SELECT, regex);
    }

    /**
     * Загрузка файла.
     */
    public void uploadFile() {
        SELECT_PICTURE_BUTTON.uploadFile(new File("src/test/resources/1.png"));
    }

    /**
     * Проверка загрузки файла.
     */
    public void checkUploadFile() {
        SELECT_PICTURE_BUTTON.shouldBe(value("1.png"));
    }

    /**
     * Получение значения из поля "Mobile Number".
     */
    public String getMobileNumber() {
        return MOBILE_INPUT.getValue();
    }

    /**
     * Проверка, что в поле "Mobile Number" значение не больше 10 символов.
     */
    public void checkLengthMobile() {
        boolean validLength = getMobileNumber().length() <= 10;
        Assertions.assertTrue(validLength, "Длинна значения больше 10 символов");
    }

    /**
     * Выбор даты плана.
     *
     * @param date дата
     */
    public void selectDate(@Nonnull LocalDate date) {
        DatePickerUtils.selectDate(DATE_PICKER, date);
    }

    /**
     * Установка значения "Male" в поле "Gender".
     */
    public void setGender() {
        RadioButtonUtils.setValue(MALE_RADIO_BUTTON);
    }

    /**
     * Установка значения в поле "Current Address".
     */
    public void setCurrentAddress(@Nonnull String value) {
        InputUtils.setValue(CURRENT_ADDRESS_INPUT, value);
    }


    /**
     * Установка значения в поле "Mobile".
     */
    public void setMobile(@Nonnull String value) {
        InputUtils.setValue(MOBILE_INPUT, value);
    }

    /**
     * Установка значения в поле "First Name".
     */
    public void setFirstName(@Nonnull String value) {
        InputUtils.setValue(FIRST_NAME_INPUT, value);
    }

    /**
     * Установка значения в поле "Last Name".
     */
    public void setLastName(@Nonnull String value) {
        InputUtils.setValue(LAST_NAME_INPUT, value);
    }

    /**
     * Установка значения в поле "Email".
     */
    public void setEmail(@Nonnull String value) {
        InputUtils.setValue(EMAIL_INPUT, value);
    }

    /**
     * Проверяем обязательность заполнения полей.
     */
    public void checkMandatoryFields() {
        Stream.of(
                        FIRST_NAME_INPUT,
                        LAST_NAME_INPUT,
                        MOBILE_INPUT
                       )
                .forEach(el -> Assertions.assertEquals("rgb(220, 53, 69)", el.getCssValue("border-color")));
    }

    /**
     * Нажатие на кнопку "Submit".
     */
    public void clickSubmit() {
        SUBMIT_BUTTON.click();
    }

}
