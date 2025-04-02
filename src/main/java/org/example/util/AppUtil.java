package org.example.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppUtil {
    public static String dateTimeParser(LocalDateTime time) {
        return dateTimeParser(time, "yyyy-MM-dd HH:mm:ss");
    }
    public static String dateTimeParser(LocalDateTime time, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return time.format(dateTimeFormatter);
    }
}
