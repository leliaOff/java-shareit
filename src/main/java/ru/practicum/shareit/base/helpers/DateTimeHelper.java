package ru.practicum.shareit.base.helpers;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeHelper {
    public static Instant toInstant(String input) {
        String pattern = "yyyy-MM-dd'T'HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.US);
        LocalDateTime localDateTime = LocalDateTime.parse(input, dateTimeFormatter);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return zonedDateTime.toInstant();
    }

    public static String toString(Instant instant) {
        return DateTimeFormatter
                .ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                .withZone(ZoneId.systemDefault())
                .format(instant);
    }
}
