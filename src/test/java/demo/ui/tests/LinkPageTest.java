package demo.ui.tests;

import com.codeborne.selenide.Selenide;
import demo.ui.pages.LinkPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.WebDriverConditions.urlContaining;

@Story("Link Page")
class LinkPageTest extends GameStoreApplicationTest{
    private static LinkPage page;
    public static final String REDIRECT_URL = "https://www.google.com/";

    @Test
    @Tag("readonly")
    @Order(1)
    @Description("Проверка формы")
    @DisplayName("01. Проверка формы")
    void testForm() {
        openStartPage();
        page = selectMenu("Link Page", LinkPage.class);
        checkValid();
    }

    @Test
    @Tag("readonly")
    @Order(2)
    @Description("Проверка работы ссылки")
    @DisplayName("02. Проверка работы ссылки")
    void testLink() {
        openStartPage();
        page = selectMenu("Link Page", LinkPage.class);
        checkLink();
    }

    private void checkLink() {
        Allure.step("Нажимаем на 'link'",
                () -> page.clickLink());
        Allure.step("Проверить, что страница переходит по адресу " + REDIRECT_URL,
                () -> Selenide.webdriver().shouldHave(urlContaining(REDIRECT_URL)));
    }

    @Step("Проверяем, что экранная форма содержит заголовок и ссылку")
    private void checkValid() {
        page.checkValid();
    }

}
