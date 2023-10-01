package demo.ui.pages;
/**
 * Базовая страница с функцией проверки состояния.
 */
public interface CheckedPage {
    /**
     * Базовая проверка.
     *
     * @throws RuntimeException при ошибках
     */
    void checkValid();

}
