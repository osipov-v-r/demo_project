package demo.ui.tests;

import com.github.javafaker.Faker;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
@Tag("web")
@Epic("Alerts, Frame & Windows")
@Feature("Alerts, Frame & Windows")
@Owner("Vladimir Osipov")
@Getter
abstract class AlertsFrameWindowTest extends AbstractTest{
    Faker faker = new Faker();
    private final String name = faker.name().firstName();
    @Override
    void openStartPage() {
        super.openStartPage();
        selectAFM();
    }

    @Step("Выбрать раздел меню 'Alerts, Frame & Windows'")
    protected void selectAFM() {
        Assertions.assertNotNull(startPage, "Не инициализирована стартовая страница");
        startPage.selectAFM();
    }

}
