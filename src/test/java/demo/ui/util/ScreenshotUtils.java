package demo.ui.util;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.OutputType;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@UtilityClass
@Log4j2
public class ScreenshotUtils {
    /**
     * Добавляет снимок экрана
     *
     * @param caption название
     */
    public static void takeScreenshot(@Nonnull String caption) {
        attachment(caption, Selenide.screenshot(OutputType.BYTES));
    }

    /**
     * Добавляет скриншот элемента.
     *
     * @param caption название
     */
    public static void takeScreenshot(@Nonnull String caption, @Nonnull SelenideElement el) {
        attachment(caption, el.getScreenshotAs(OutputType.BYTES));
    }

    /**
     * Проверка условия и завал теста с вложением снимка экрана.
     *
     * @param expression условие, которое должно быть положительно
     * @param error текст ошибки
     */
    public static void check(boolean expression, @Nonnull String error) {
        if (!expression) {
            takeScreenshot("Скриншот " + LocalDateTime.now(ZoneId.of("Europe/Moscow")));
            fail(error);
        }
    }

    private static void attachment(@Nonnull String caption, byte[] screenshot) {
        assertNotNull(screenshot);
        try (InputStream is = new ByteArrayInputStream(screenshot)) {
            Allure.attachment(caption, is);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            fail(ex);
        }
    }
}
