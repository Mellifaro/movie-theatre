package ua.epam.spring.hometask.util;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SeatParsingUtils {

    public static Set<Long> parseString(String string){
        return Stream.of(string.split(","))
                .filter(seat -> !seat.trim().isEmpty())
                .map(seat -> Long.parseLong(seat.trim()))
                .collect(Collectors.toSet());
    }
}
