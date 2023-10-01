package demo.ui.util;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.sleep;
import static demo.ui.util.ScreenshotUtils.takeScreenshot;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Функции для работы с таблицей.
 */
@UtilityClass
public class TableUtils {
    public enum WaitOptions {
        ALLOW_EMPTY,
    }

    /**
     * Выбор данных из таблицы.
     *
     * @param timeout таймаут ожидания (нужного числа полей данных в имеющихся строках)
     * @param cols количество ожидаемых полей данных
     * @param mapper преобразование полей данных в объект
     * @param waitOptions опции выборки
     * @param <T> тип объекта, определяемый данными строки
     * @return список объектов, построенных по данным из таблицы
     */
    @Nonnull
    public static <T> List<T> getData(@Nonnull SelenideElement elTable,
                                      long timeout,
                                      int cols,
                                      @Nonnull Function<ElementsCollection, T> mapper,
                                      @Nonnull WaitOptions... waitOptions) {
        try {
            long start = System.currentTimeMillis();
            while (!elTable.exists()
                    || elTable.$$(byXpath("div[@class='rt-tbody']//div[@role='row']")).asFixedIterable().stream()
                    .anyMatch(row -> row.$$(byXpath("div[@class='rt-td']")).snapshot().texts().size() < cols)) {
                if (System.currentTimeMillis() - start > timeout) {
                    if (Arrays.asList(waitOptions).contains(WaitOptions.ALLOW_EMPTY)) {
                        break;
                    }
                    takeScreenshot("Таймаут");
                    fail("Неудачная загрузка данных, неожиданные данные: " +
                            elTable.$$(byXpath("div[@class='rt-tbody']//div[@role='row']")).asFixedIterable().stream()
                                    .filter(row -> row.$$(byXpath("div[@class='rt-td']")).snapshot().texts().size() < cols)
                                    .collect(Collectors.toSet()));
                }
                sleep(300);
            }
            return elTable.shouldBe(Condition.visible)
                    .$$(byXpath("div[@class='rt-tbody']//div[@role='row']"))
                    .filter(not(empty))
                    .asFixedIterable().stream()
                    .map(row -> row.scrollIntoView(true).$$(byXpath("div[@class='rt-td']")).snapshot())
                    .filter(tds -> tds.size() >= cols)
                    .map(mapper)
                    .collect(Collectors.toList());
        } catch (RuntimeException ex) {
            takeScreenshot(ex.getMessage());
            throw ex;
        }
    }

    /**
     * Проверка заголовка таблицы.
     *
     * @param table элемент таблицы
     * @param header поля заголовка
     */
    public static void checkHeader(@Nonnull SelenideElement table, @Nonnull String... header) {
        List<String> texts =
                table.$$(byXpath("descendant-or-self::div[@class='rt-thead -header']/div[@class='rt-tr']/div[@class='rt-th rt-resizable-header -cursor-pointer']/div[@class='rt-resizable-header-content']"))
                        .shouldHave(CollectionCondition.sizeGreaterThan(0))
                        .filter(not(empty))
                        .asFixedIterable()
                        .stream()
                        .map(SelenideElement::text)
                        .filter(StringUtils::isNotBlank)
                        .map(String::trim)
                        .collect(Collectors.toList());
        table.scrollIntoView("{behavior: \"instant\", block: \"end\", inline: \"end\"}");
        table.$$(byXpath("descendant-or-self::div[@class='rt-thead -header']/div[@class='rt-tr']/div[@class='rt-th rt-resizable-header -cursor-pointer']/div[@class='rt-resizable-header-content']"))
                .filter(not(empty))
                .asFixedIterable()
                .stream()
                .map(SelenideElement::text)
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .filter(text -> !texts.contains(text))
                .forEachOrdered(texts::add);
        if (!texts.stream().allMatch(actual -> Arrays.stream(header).anyMatch(expected -> match(expected, actual)))) {
            fail(String.format("Table doesn't contain expected fields.\n"
                            + "Expected: %s\n"
                            + "Actual:   %s",
                    String.join(", ", header),
                    String.join(", ", texts)));
        }
    }

    /**
     * Проверяет совпадение строк.
     * Если ожидаемая строка начинается со строки "Regex: ",
     * то проверяется по регулярному выражению (сам "маркер" Regex: отсекается).
     */
    private boolean match(@Nonnull String expected, String actual) {
        String regexPrefix = "Regex: ";
        boolean result;
        if (expected.startsWith(regexPrefix)) {
            String regex = expected.substring(regexPrefix.length());
            Pattern p = Pattern.compile(regex, Pattern.UNICODE_CASE);
            result = p.matcher(actual).matches();
        } else {
            result = StringUtils.equals(expected, actual);
        }
        return result;
    }
}
