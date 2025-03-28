package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppUtil {
    public static String localDateTimeParser(LocalDateTime time) {
        return localDateTimeParser(time, "yyyy-MM-dd HH:mm:ss");
    }
    public static String localDateTimeParser(LocalDateTime time, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return time.format(dateTimeFormatter);
    }
}
