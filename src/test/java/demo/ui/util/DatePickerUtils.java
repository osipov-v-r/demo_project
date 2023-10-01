package demo.ui.util;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.attributeMatching;
import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.editable;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static demo.ui.util.ScreenshotUtils.takeScreenshot;
import static java.util.Locale.US;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Функции работы с элементом Date Picker.
 */
@UtilityClass
public class DatePickerUtils {
    private static final ElementsCollection YEAR_PICKERS =
            $$(byXpath("//div[@class='rc-picker-date-panel']//button[@class='rc-picker-year-btn']"));
    private static final String YEAR_PATH = "//div[@class='rc-picker-cell-inner' and text()='%d']";
    private static final ElementsCollection MONTH_PICKERS =
            $$(byXpath("//div[@class='rc-picker-date-panel']//button[@class='rc-picker-month-btn']"));
    private static final String MONTH_PATH = "//td[@title='%04d-%02d']";
    private static final String DAY_PATH = "//td[@title='%04d-%02d-%02d']";
    private static final String HOUR_PATH    = "//div[@class='rc-picker-time-panel']//ul[1]/li/div[text()='%s']";
    private static final String MINUTES_PATH = "//div[@class='rc-picker-time-panel']//ul[2]/li/div[text()='%s']";
    private static final SelenideElement APPLY_BUTTON = $$(byXpath("//button/span[text()='Применить']/..")).last();


    /**
     * Находит элемент по метке.
     *
     * @param label метка
     */
    @Nonnull
    public SelenideElement getByLabel(@Nonnull String label) {
        return $(byXpath(String.format("//label[text()='%s']/ancestor::div[contains(@id, 'DatePicker-') or contains(@class, 'DateTimeComponent')]", label)));
    }

    /**
     * Для указанного элемента выбирает дату.
     *
     * @param datePicker элемент Date Picker
     * @param date дата
     */
    public void selectDate(@Nonnull SelenideElement datePicker, @Nonnull LocalDate date) {
        SelenideElement input = datePicker.$(byXpath("descendant::input[@type='text']"))
                .shouldBe(visible, enabled, editable);
        input.sendKeys(Keys.CONTROL + "A");
        for (char ch : date.format(DateTimeFormatter.ofPattern("dd MMM yyyy").localizedBy(US)).toCharArray()) {
            input.sendKeys(String.valueOf(ch));
        }
        input.sendKeys(Keys.ENTER);
    }

    /**
     * Для указанного элемента выбирает дату/время.
     *
     * @param datePicker элемент Date Picker
     * @param date дата/время
     */
    public void selectDateTime(@Nonnull SelenideElement datePicker, @Nonnull LocalDateTime date) {
        SelenideElement input = datePicker.$(byXpath("descendant::input[@type='text']"))
                .shouldBe(visible, enabled, editable);
        input.sendKeys(Keys.CONTROL + "A");
        for (char ch : date.format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss")).toCharArray()) {
            input.sendKeys(String.valueOf(ch));
        }
        input.sendKeys(Keys.ENTER);
    }

    /**
     * Для указанного элемента очищает дату.
     *
     * @param datePicker элемент Date Picker
     */
    public void clearDate(@Nonnull SelenideElement datePicker) {
        datePicker.$(byXpath("descendant::input[@type='text']"))
                .shouldBe(editable)
                .sendKeys(Keys.CONTROL + "A", Keys.BACK_SPACE, Keys.ENTER);
    }

    /**
     * Проверка доступности редактирования.
     *
     * @param datePicker элемент Date Picker
     */
    public void checkEnabled(@Nonnull SelenideElement datePicker) {
        datePicker.$(byXpath("descendant::input[@type='text']")).shouldBe(enabled);
    }

    /**
     * Проверка недоступности редактирования.
     *
     * @param datePicker элемент Date Picker
     */
    public void checkDisabled(@Nonnull SelenideElement datePicker) {
        datePicker.$(byXpath("descendant::input[@type='text']")).shouldBe(disabled);
    }

    /**
     * Для указанного элемента выбирает дату.
     *
     * @param datePicker элемент Date Picker
     * @param date дата
     */
    public void selectDateManual(@Nonnull SelenideElement datePicker, @Nonnull LocalDate date) {
        WaitUtils.waitUntil(
                () -> !YEAR_PICKERS.filter(visible).isEmpty(),
                () -> datePicker.shouldBe(visible, enabled).click(),
                100,
                1000);
        YEAR_PICKERS.filter(visible).first().click();
        $(byXpath(String.format(YEAR_PATH, date.getYear()))).shouldBe(visible).click();
        MONTH_PICKERS.filter(visible).first().click();
        $(byXpath(String.format(MONTH_PATH, date.getYear(), date.getMonthValue()))).shouldBe(visible).click();
        $$(byXpath(String.format(DAY_PATH, date.getYear(), date.getMonthValue(), date.getDayOfMonth())))
                .filter(visible)
                .first()
                .click();
    }

    /**
     * Для указанного элемента выбирает дату/время.
     *
     * @param datePicker элемент Date Picker
     * @param date дата/время
     */
    public void selectDateTimeManual(@Nonnull SelenideElement datePicker, @Nonnull LocalDateTime date) {
        selectDate(datePicker, date.toLocalDate());
        $$(byXpath(String.format(HOUR_PATH, date.format(DateTimeFormatter.ofPattern("HH")))))
                .last()
                .scrollIntoView(false)
                .shouldBe(visible)
                .click();
        $$(byXpath(String.format(MINUTES_PATH, date.format(DateTimeFormatter.ofPattern("mm")))))
                .last()
                .scrollIntoView(false)
                .shouldBe(visible)
                .click();
        APPLY_BUTTON.shouldBe(visible, enabled).click();
    }

    /**
     * Проверка установленной даты.
     *
     * @param date дата
     */
    public void checkDate(@Nonnull SelenideElement el, @Nonnull LocalDate date) {
        el.$(byXpath("descendant::input[@type='text']"))
                .shouldHave(attribute("value", date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
    }

    /**
     * Проверка установленной даты.
     *
     * @param date дата
     */
    public void checkDateTime(@Nonnull SelenideElement el, @Nonnull LocalDateTime date) {
        LocalDateTime actual = DateUtils.parseDateTimeLong(
                Optional.ofNullable(el.$(byXpath("descendant::input[@type='text']"))
                                .shouldHave(attributeMatching("value", "\\d{2}.\\d{2}.\\d{4}\\s+\\d{2}:\\d{2}"))
                                .getAttribute("value"))
                        .orElseThrow(() -> new AssertionError("Неверный формат даты")));
        if (ChronoUnit.MINUTES.between(date, actual) > 1) {
            takeScreenshot("Неверная дата");
            fail("Неверная дата");
        }
    }
}
