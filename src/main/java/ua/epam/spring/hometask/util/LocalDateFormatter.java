package ua.epam.spring.hometask.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateFormatter{

    public static Timestamp convertToTimestamp(LocalDate date){
        return Timestamp.valueOf(date.atStartOfDay());
    }
}
