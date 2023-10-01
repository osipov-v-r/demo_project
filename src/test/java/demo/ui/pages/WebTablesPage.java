package demo.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import demo.ui.dto.TableRecord;
import demo.ui.util.ButtonUtils;
import demo.ui.util.InputUtils;
import demo.ui.util.TableUtils;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Страница "Web Tables".
 */
public class WebTablesPage implements PageWithTables {
    private static final SelenideElement CAPTION = $(byXpath("//div[@class='main-header' and text()='Web Tables']"));
    private static final SelenideElement SEARCH_INPUT = InputUtils.getByTitle("Type to search");
    private static final SelenideElement ADD_BUTTON = ButtonUtils.getByCaption("Add");

    @Override
    public void checkValid() {
        Stream.of(
                        CAPTION,
                        SEARCH_INPUT,
                        ADD_BUTTON)
                .forEach(el -> el.shouldBe(visible));
    }

    @Override
    public void checkTableHeader(@Nonnull String... header) {
        TableUtils.checkHeader($(byXpath("//div[contains(@class, 'ReactTable')]")), header);
    }

    /**
     * Нажимаем на кнопку "Add".
     */
    public void clickAddButton() {
        ADD_BUTTON.click();
    }

    /**
     * Выбор данных из таблицы.
     */
    @Nonnull
    public List<TableRecord> getData(long timeout, @Nonnull TableUtils.WaitOptions... waitOptions) {
        return TableUtils.getData($(byXpath("//div[@class='rt-table']")),
                timeout, 7, this::getInfo, waitOptions);
    }

    @Nonnull
    private TableRecord getInfo(@Nonnull ElementsCollection cells) {
        return TableRecord.builder()
                .firstName(cells.get(0).getText())
                .lastName(cells.get(1).getText())
                .age(Integer.valueOf(cells.get(2).getText()))
                .email(cells.get(3).getText())
                .salary(Long.valueOf(cells.get(4).getText()))
                .department(cells.get(5).getText())
                .build();
    }

    /**
     * Редактирование созданное записи (нажатие кнопки).
     */
    public void clickEdit(@Nonnull TableRecord tableRecord) {
        for (SelenideElement elRow : $$(byXpath("//div[@class='rt-table']/div[@class='rt-tbody']//div[@role='row']"))) {
            if (tableRecord.equals(getInfo(elRow.$$(byXpath("div[@class='rt-td']"))))) {
                elRow.scrollIntoView(true)
                        .$$(byXpath("..//div[@class='action-buttons']//*[local-name() = 'path']"))
                        .first()
                        .shouldBe(visible)
                        .click();
                return;
            }
        }
        fail("Запись не найдена: " + tableRecord);
    }

    /**
     * Редактирование созданное записи (нажатие кнопки).
     */
    public void clickDelete(@Nonnull TableRecord tableRecord) {
        for (SelenideElement elRow : $$(byXpath("//div[@class='rt-table']/div[@class='rt-tbody']//div[@role='row']"))) {
            if (tableRecord.equals(getInfo(elRow.$$(byXpath("div[@class='rt-td']"))))) {
                elRow.scrollIntoView(true)
                        .$$(byXpath("..//div[@class='action-buttons']//*[local-name() = 'path']"))
                        .last()
                        .shouldBe(visible)
                        .click();
                return;
            }
        }
        fail("Запись не найдена: " + tableRecord);
    }

}
