package demo.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import demo.ui.util.DndUtils;
import javax.annotation.Nonnull;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Страница "Sortable".
 */
public class SortablePage implements CheckedPage{
    private static final SelenideElement CAPTION = $(byXpath("//div[@class='main-header' and text()='Sortable']"));

    @Override
    public void checkValid() {
        CAPTION.shouldBe(visible);
    }

    /**
     * Проверка перетаскивания - перетаскиваем один элемент на другой.
     */
    public void checkDnD(@Nonnull String source, @Nonnull String target) {
        ElementsCollection collection =
                $$(byXpath(String.format("//div[@class ='vertical-list-container mt-4']//div[contains(text(), '%s') or  contains(text(),'%s')]",
                        source, target)));
        collection.shouldHave(size(2));
        SelenideElement elTarget = collection.get(0);
        SelenideElement elSource = collection.get(1);
        String textSource = collection.last().getText();
        DndUtils.dragAndDrop(elSource, elTarget);
        assertEquals(textSource, collection.first().getText(), "Порядок не изменился после перетаскивания");
    }

}
