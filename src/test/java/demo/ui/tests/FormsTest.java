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
@Epic("Forms")
@Feature("Forms")
@Owner("Vladimir Osipov")
@Getter
abstract class FormsTest extends AbstractTest {
    Faker faker = new Faker();
    private final String firstName = faker.name().firstName();
    private final String lastName = faker.name().lastName();
    private final String mobile = faker.phoneNumber().subscriberNumber(10);
    private final String currentAddress = faker.address().streetAddress();
    private final String email = firstName.toLowerCase() + lastName.toLowerCase() + "@gmail.com";

    @Override
    void openStartPage() {
        super.openStartPage();
        selectForms();
    }

    @Step("Выбрать раздел меню 'Forms'")
    protected void selectForms() {
        Assertions.assertNotNull(startPage, "Не инициализирована стартовая страница");
        startPage.selectForms();
    }

}
