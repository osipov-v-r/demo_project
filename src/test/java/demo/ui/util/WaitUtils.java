package demo.ui.util;

import io.qameta.allure.Allure;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import static com.codeborne.selenide.Selenide.sleep;
import static demo.ui.util.ScreenshotUtils.takeScreenshot;
import static org.junit.jupiter.api.Assertions.fail;
@UtilityClass
public class WaitUtils {
    /**
     * Ожидание с периодическим выполнением действия.
     *
     * @param breakCondition поставщик ожидаемого положительного результата
     * @param action действие
     * @param pollMillis период выполнения периодического действия
     * @param timeoutMillis таймаут выполнения, после которого тест проваливается
     */
    public static void waitUntil(
            @Nonnull Supplier<Boolean> breakCondition,
            @Nonnull Runnable action,
            long pollMillis,
            long timeoutMillis,
            String error) {
        if (!waitUntil(breakCondition, action, pollMillis, timeoutMillis)) {
            takeScreenshot("Таймаут");
            fail(error);
        }
    }

    /**
     * Ожидание с периодическим выполнением действия.
     *
     * @param breakCondition поставщик ожидаемого положительного результата
     * @param action действие
     * @param pollMillis период выполнения периодического действия
     */
    public static boolean waitUntil(
            @Nonnull Supplier<Boolean> breakCondition,
            @Nonnull Runnable action,
            long pollMillis,
            long timeoutMillis) {
        long start = System.currentTimeMillis();
        int index = 1;
        do {
            Allure.step("Попытка " + index++, action::run);
            if (breakCondition.get()) {
                return true;
            }
            sleep(pollMillis);
        } while (System.currentTimeMillis() - start < timeoutMillis);
        return false;
    }
}
