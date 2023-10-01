package demo.ui.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

/**
 * Функции для разбора даты/времени.
 */
@UtilityClass
public class DateUtils {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy");
    private static final DateTimeFormatter DATE_FORMATTER_LONG = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    private static final DateTimeFormatter DATE_TIME_FORMATTER_LONG = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Nonnull
    public LocalDate parseDate(@Nonnull String text) {
        return LocalDate.parse(wrapDateTime(text), DATE_FORMATTER);
    }

    @Nonnull
    public LocalDate parseDateLong(@Nonnull String text) {
        return LocalDate.parse(wrapDateTime(text), DATE_FORMATTER_LONG);
    }

    @Nonnull
    public LocalDateTime parseDateTime(@Nonnull String text) {
        return LocalDateTime.parse(wrapDateTime(text), DATE_TIME_FORMATTER);
    }

    @Nonnull
    public LocalDateTime parseDateTimeLong(@Nonnull String text) {
        return LocalDateTime.parse(wrapDateTime(text), DATE_TIME_FORMATTER_LONG);
    }

    @Nonnull
    public String formatDate(@Nonnull LocalDate date) {
        return DATE_FORMATTER.format(date);
    }

    @Nonnull
    public String formatDateTime(@Nonnull LocalDateTime date) {
        return DATE_TIME_FORMATTER.format(date);
    }

    @Nonnull
    public String formatDateTimeLong(@Nonnull LocalDateTime date) {
        return DATE_TIME_FORMATTER_LONG.format(date);
    }

    @Nonnull
    private String wrapDateTime(@Nonnull String date) {
        return date.replaceAll("\\s{2,}", " ");
    }
}
