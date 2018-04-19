package ua.epam.spring.hometask.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class LocalDateFormatter{
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static Timestamp convertToTimestamp(LocalDate date){
        return Timestamp.valueOf(date.atStartOfDay());
    }

//    public static LocalDateTime parseLocalDateTime(Timestamp timestamp) {
//        LocalDateTime localDateTime =
//                LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getTime()),
//                        TimeZone.getDefault().toZoneId());
//        return localDateTime;
//    }
//
//    public static LocalDate parseLocalDate(Timestamp timestamp) {
//        LocalDate localDate =
//                LocalDate.ofInstant(Instant.ofEpochMilli(timestamp.getTime()),
//                        TimeZone.getDefault().toZoneId());
//        return localDate;
//    }
}
