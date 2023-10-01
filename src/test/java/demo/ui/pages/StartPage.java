package demo.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;

/**
 * Стартовая страница приложения с логотипом, подсказкой и ссылками на формы.
 */
public class StartPage {
    private static final SelenideElement ELEMENTS_MENU = $x("//h5[text()='Elements']");
    private static final SelenideElement FORMS_MENU = $x("//h5[text()='Forms']");
    private static final SelenideElement AFW_MENU = $x("//h5[text()='Alerts, Frame & Windows']");
    private static final SelenideElement WIDGETS_MENU = $x("//h5[text()='Widgets']");
    private static final SelenideElement INTERACTIONS_MENU = $x("//h5[text()='Interactions']");
    private static final  SelenideElement GSA_MENU = $x("//h5[text()='Game Store Application']");

    public void checkValid() {
        Stream.of(
                        ELEMENTS_MENU,
                        FORMS_MENU,
                        AFW_MENU,
                        WIDGETS_MENU,
                        INTERACTIONS_MENU,
                        GSA_MENU)
                .forEach(el -> el.shouldBe(visible));
    }

    /**
     * Выбор раздела "Elements".
     */
    public void selectElements() {
        ELEMENTS_MENU.click();
    }

    /**
     * Выбор раздела "Forms".
     */
    public void selectForms() {
        FORMS_MENU.click();
    }

    /**
     * Выбор раздела "Alerts, Frame & Windows".
     */
    public void selectAFM() {
        AFW_MENU.click();
    }

    /**
     * Выбор раздела "Widgets".
     */
    public void selectWidgets() {
        WIDGETS_MENU.click();
    }

    /**
     * Выбор раздела "Interactions".
     */
    public void selectInteractions() {
        INTERACTIONS_MENU.click();
    }

    /**
     * Выбор раздела "Game Store Application".
     */
    public void selectGSA() {
        GSA_MENU.click();
    }

    /**
     * Выбирает указанный пункт меню для текущего раздела.
     * Возможно вложение. Например: Установка десульфурации чугуна / Регистрация налива
     *
     * @param menuPath путь до пункта меню
     */
    @Nonnull
    @Step("Выбрать меню {menuPath}")
    public String selectMenu(@Nonnull String menuPath) {
        String[] menuItems = menuPath.split("\\s+/\\s+");
        Stream.of(menuItems)
                .forEach(menuItem -> {
                    $$(byXpath(String.format("//div[contains(@class, 'element-list collapse')]//span[text()='%s']", menuItem)))
                            .filter(visible)
                            .last()
                            .scrollIntoView(true)
                            .click();
                    sleep(500);
                });
        return menuItems[menuItems.length - 1];
    }

}
