package util;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/* * DateUtils.java
 * Utility class for formatting LocalDateTime objects to a standard string format.
 */
public class DateUtils {

    private static final DateTimeFormatter STANDARD_FORMATTER =
        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static String format(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(STANDARD_FORMATTER) : "";
    }
}
