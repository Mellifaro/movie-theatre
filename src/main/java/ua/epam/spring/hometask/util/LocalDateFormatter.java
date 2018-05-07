package ua.epam.spring.hometask.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateFormatter{
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static Timestamp convertToTimestamp(LocalDate date){
        return Timestamp.valueOf(date.atStartOfDay());
    }

}
