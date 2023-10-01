package demo.ui.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import demo.ui.pages.CheckedPage;
import demo.ui.pages.StartPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import java.time.LocalDate;
import javax.annotation.Nonnull;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

/**
 * Базовый класс для тестов с настройкой основной конфигурации, общими функциями.
 */
@Log4j2
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
abstract class AbstractTest {
    private static final String ALLURE_LISTENER = "allure";
    private static final String DEFAULT_SCREEN_SIZE = "1920x1080";
    private static final String START_URL_DEFAULT = "http://85.192.34.140:8081/";
    StartPage startPage;

    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener(ALLURE_LISTENER, new AllureSelenide());
        Configuration.headless = true;
        Configuration.browserSize = DEFAULT_SCREEN_SIZE;
        Configuration.pageLoadStrategy = "eager";
        Configuration.pageLoadTimeout = 15000;
        Configuration.timeout = 45000;
    }

    @SneakyThrows
    @AfterAll
    static void afterAll() {
        SelenideLogger.removeListener(ALLURE_LISTENER);
    }

    void openStartPage() {
        while (true) {
            try {
                Allure.step("Открыть стартовую страницу", () -> {
                    startPage = open(START_URL_DEFAULT, StartPage.class);
                });
                break;
            } catch (Throwable th) {
                log.error(th.getMessage(), th);
                Allure.step("Неудача - закрываем окно, чтобы перезайти", this::closeWindow);
            }
        }
            startPage = checkStartPage();
    }

    @Nonnull
    @Step("Проверить, что отобразилась стартовая страница")
    StartPage checkStartPage() {
        StartPage startPage = page(StartPage.class);
        Allure.step("Отображается все разделы меню", startPage::checkValid);
        return startPage;
    }

    @Nonnull
    @Step("Выбрать меню {menuPath}")
    <T extends CheckedPage> T selectMenu(@Nonnull String menuPath, @Nonnull Class<T> cls) {
        String pageName = startPage.selectMenu(menuPath);
        Object page = page(cls);
        T result = cls.cast(page);
        Allure.step("Открылась страница: " + pageName, result::checkValid);
        return result;
    }

    @Step("Закрыть окно")
    void closeWindow() {
        try {
            Selenide.closeWindow();
        } catch (Throwable th) {
            log.trace(th.getMessage(), th);
        }
    }

    /**
     * Дата, используемая по умолчанию.
     */
    @Nonnull
    LocalDate getDate() {
        return LocalDate.now().minusYears(20);
    }

}
