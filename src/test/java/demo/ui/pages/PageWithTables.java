package demo.ui.pages;

import javax.annotation.Nonnull;

/**
 * Страница с таблицами.
 */
public interface PageWithTables extends CheckedPage {
    /**
     * Проверка заголовка таблицы.
     */
    void checkTableHeader(@Nonnull String... header);

}
