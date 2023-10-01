package demo.ui.util;

import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import static com.codeborne.selenide.Selenide.actions;

/**
 * Функции дран-н-дропа.
 */
@UtilityClass
public class DndUtils {
    /**
     * Перетаскивание одного элемента на другой.
     */
    public void dragAndDrop(@Nonnull SelenideElement elSource, @Nonnull SelenideElement elTarget) {
        actions()
                .moveToElement(elSource)
                .clickAndHold(elSource)
                .pause(Duration.ofSeconds(1))
                .moveByOffset(0, 5)
                .moveToElement(elTarget)
                .pause(Duration.ofSeconds(1))
                .release()
                .pause(Duration.ofSeconds(1))
                .perform();
    }

}
