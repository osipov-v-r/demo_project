package demo.ui.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Tag;

import static org.junit.jupiter.api.Assertions.assertNotNull;
@Tag("web")
@Epic("Interactions")
@Feature("Interactions")
@Owner("Vladimir Osipov")
abstract class InteractionsTest extends AbstractTest {
    @Override
    void openStartPage() {
        super.openStartPage();
        selectInteractions();
    }

    @Step("Выбрать раздел меню 'Interactions'")
    protected void selectInteractions() {
        assertNotNull(startPage, "Не инициализирована стартовая страница");
        startPage.selectInteractions();
    }

}
