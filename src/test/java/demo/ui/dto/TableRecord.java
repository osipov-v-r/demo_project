package demo.ui.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Данные записи в таблице
 */
@Data
@Builder
public class TableRecord {
    /**
     * Имя
     */
    private String firstName;
    /**
     * Фамилия
     */
    private String lastName;
    /**
     * Почтовый ящик
     */
    private String email;
    /**
     * Возраст
     */
    private Integer age;
    /**
     * Зарплата
     */
    private Long salary;
    /**
     * Отделение
     */
    private String department;

}
