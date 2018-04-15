package ua.epam.spring.hometask.domain.testdata;

import ua.epam.spring.hometask.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestData {
    public static User admin;
    public static User user;

    public static Event theLordOfRings;
    public static Event scaryMovie;

    public static Auditorium greenAuditorium;
    public static Auditorium redAuditorium;

    public static Ticket adminTicket;
    public static Ticket userTicket;

    public static final LocalDateTime firstEventFirstDate = LocalDateTime.of(2018, 5, 5, 20, 45);
    public static final LocalDateTime firstEventSecondDate = LocalDateTime.of(2018, 5, 6, 18, 30);
    public static final LocalDateTime secondEventFirstDate = LocalDateTime.of(2018, 4, 25, 18, 30);

    static {
        initEntities();
    }

    private static void initEntities(){
        //creating users
        admin = new User("Ivan", "Kravchuk", "kravchukivan@gmail.com", LocalDate.of(1985, 5, 1));
        user = new User("Alek", "Onopenko", "onopemkoalek@gmail.com", LocalDate.of(1993, 4, 7));
        admin.setId(100L);
        user.setId(101L);

        //creating auditoriums
        greenAuditorium = new Auditorium("green", 50, Stream.of(10L, 11L, 12L).collect(Collectors.toSet()));
        redAuditorium = new Auditorium("red", 75, Stream.of(5L, 6L, 7L, 8L).collect(Collectors.toSet()));

        //creating events
        theLordOfRings = new Event("The Lord Of The Rings", 65.00, EventRating.HIGH);
        theLordOfRings.setAirDates(new TreeSet<LocalDateTime>(){{
            add(firstEventFirstDate);
            add(firstEventSecondDate);
        }});
        theLordOfRings.setAuditoriums(new TreeMap<LocalDateTime, Auditorium>(){{
            put(firstEventFirstDate, greenAuditorium);
            put(firstEventSecondDate, redAuditorium);
        }});
        theLordOfRings.setId(100L);

        scaryMovie = new Event("Scary movie", 60.00, EventRating.MID);
        scaryMovie.setAirDates(new TreeSet<LocalDateTime>(){{
            add(secondEventFirstDate);
        }});
        scaryMovie.setAuditoriums(new TreeMap<LocalDateTime, Auditorium>(){{
            put(secondEventFirstDate, redAuditorium);
        }});
        scaryMovie.setId(101L);

        //creating tickets
        adminTicket = new Ticket(admin.getId(), theLordOfRings.getId(), firstEventFirstDate,10L);
        userTicket = new Ticket(user.getId(), scaryMovie.getId(), secondEventFirstDate, 42L);
    }
}
