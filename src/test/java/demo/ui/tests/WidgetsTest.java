package demo.ui.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;

@Tag("web")
@Epic("Widgets")
@Feature("Widgets")
@Owner("Vladimir Osipov")
abstract class WidgetsTest extends AbstractTest {
    @Override
    void openStartPage() {
        super.openStartPage();
        selectWidgets();
    }

    @Step("Выбрать раздел меню 'Widgets'")
    protected void selectWidgets() {
        Assertions.assertNotNull(startPage, "Не инициализирована стартовая страница");
        startPage.selectWidgets();
    }

}
