package demo.ui.tests;

import demo.ui.pages.AccordianPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import javax.annotation.Nonnull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("web")
@Epic("Widgets")
@Feature("Widgets")
@Owner("Vladimir Osipov")
class AccordianTest extends WidgetsTest{
    private static AccordianPage page;

    @Test
    @Tag("readonly")
    @Order(1)
    @Story("Alerts")
    @Description("Проверка формы")
    @DisplayName("01. Проверка формы")
    void testForm() {
        try {
            openStartPage();
            page = selectMenu("Accordian", AccordianPage.class);
            checkValid();
        } finally {
            closeWindow();
        }
    }

    @Test
    @Tag("readonly")
    @Order(2)
    @Story("Alerts")
    @Description("Проверка аккордеонов")
    @DisplayName("02. Проверка аккордеонов")
    void testAccordian() {
        try {
            openStartPage();
            page = selectMenu("Accordian", AccordianPage.class);
            checkAccordian();
        } finally {
            closeWindow();
        }
    }

    private void checkAccordian() {
        String caption = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. "
                + "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer"
                + " took a galley of type and scrambled it to make a type specimen book. It has survived not only five "
                + "centuries, but also the leap into electronic typesetting, remaining essentially unchanged. "
                + "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,"
                + " and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
        checkTextInCard(0, caption);
        Allure.step("Нажимаем на раздел 'Where does it come from?'",
                () -> page.clickAccordian2());
        String caption2 = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n"
                + "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.";
        checkTextInCard(1, caption2);
        Allure.step("Нажимаем на раздел 'Why do we use it?'",
                () -> page.clickAccordian3());
        String caption3 = "It is a long established fact that a reader will be distracted by the readable content of a"
                + " page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal "
                + "distribution of letters, as opposed to using 'Content here, content here', making it look like readable English."
                + " Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text,"
                + " and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions "
                + "have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).";
        checkTextInCard(2, caption3);
        Allure.step("Нажимаем на раздел 'What is Lorem Ipsum?'",
                () -> page.clickAccordian1());
        checkTextInCard(0, caption);
    }

    @Step("Проверяем, что экранная форма содержит заголовок и кнопки с описанием")
    private void checkValid() {
        page.checkValid();
    }

    @Step("Проверяем, что экранная форма содержит в карточке текст: '{caption}'")
    private void checkTextInCard(int chart, @Nonnull String caption) {
        page.checkTextInCard(chart, caption);
    }
}
