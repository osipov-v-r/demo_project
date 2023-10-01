package demo.ui.tests;

import com.github.javafaker.Faker;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import java.util.Random;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;


@Tag("web")
@Epic("Elements")
@Feature("Elements")
@Owner("Vladimir Osipov")
@Getter
abstract class ElementsTest extends AbstractTest  {
    Faker faker = new Faker();
    private final String firstName = faker.name().firstName();
    private final String lastName = faker.name().lastName();
    private final String fullName = firstName + " " + lastName;
    private final String currentAddress = faker.address().streetAddress();
    private final String permanentAddress = faker.address().city();
    private final String email = firstName.toLowerCase() + lastName.toLowerCase() + "@gmail.com";
    Integer age() {
        Random random = new Random();
        return random.nextInt(28) + 18;
    }

    @Override
    void openStartPage() {
        super.openStartPage();
        selectElements();
    }

    @Step("Выбрать раздел меню 'Elements'")
    protected void selectElements() {
        Assertions.assertNotNull(startPage, "Не инициализирована стартовая страница");
        startPage.selectElements();
    }

}
