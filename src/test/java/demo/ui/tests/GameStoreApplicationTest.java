package demo.ui.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Tag;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("web")
@Epic("Game Store Application")
@Feature("Game Store Application")
@Owner("Vladimir Osipov")
abstract class GameStoreApplicationTest extends AbstractTest {

    @Override
    void openStartPage() {
        super.openStartPage();
        selectGSA();
    }

    @Step("Выбрать раздел меню 'Game Store Application'")
    protected void selectGSA() {
        assertNotNull(startPage, "Не инициализирована стартовая страница");
        startPage.selectGSA();
    }

}
