package demo.ui.tests;

import demo.ui.pages.SortablePage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import java.util.Arrays;
import javax.annotation.Nonnull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Story("Alerts")
public class SortableTest extends InteractionsTest {
    private static SortablePage page;

    @Test
    @Tag("readonly")
    @Order(1)
    @Description("Проверка формы")
    @DisplayName("01. Проверка формы")
    void testForm() {
        try {
            openStartPage();
            page = selectMenu("Sortable", SortablePage.class);
            checkValid();
        } finally {
            closeWindow();
        }
    }

    @Test
    @Tag("readonly")
    @Order(2)
    @Description("Проверка перемещения сток")
    @DisplayName("02. Проверка перемещения сток")
    void testStringSetup() {
        try {
            openStartPage();
            page = selectMenu("Sortable", SortablePage.class);
            checkStringSetup("One","Two","Three","Four", "Five", "Six");
        } finally {
            closeWindow();
        }
    }

    @Step("Проверяем настройку сток таблицы")
    private void checkStringSetup(@Nonnull String... columns) {
        Allure.step(String.format("Строки %s перетаскиваются, меняется порядок строк %s и %s",
                        Arrays.toString(columns), columns[0], columns[1]),
                () -> page.checkDnD(columns[1], columns[0]));
    }

    @Step("Проверяем, что экранная форма содержит заголовок'")
    private void checkValid() {
        page.checkValid();
    }

}
