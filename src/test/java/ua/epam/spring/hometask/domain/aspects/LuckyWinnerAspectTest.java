package ua.epam.spring.hometask.domain.aspects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.aspects.LuckyWinnerAspect;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.DiscountType;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.booking.BookingService;
import ua.epam.spring.hometask.service.user.UserService;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static ua.epam.spring.hometask.domain.testdata.TestData.*;

/**
 * Created by Viktor Skapoushchenko on 4/22/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populate_db.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ContextConfiguration(classes = AppConfig.class)
public class LuckyWinnerAspectTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private LuckyWinnerAspect luckyWinnerAspect;

    @Autowired
    private UserService userService;

    @Before
    public void init(){
        luckyWinnerAspect.setLuckyWinnerChance(2);
    }

    @Test
    public void testLuckyWinner(){
        Set<Ticket> tickets = bookingService.getTicketsFromSeats(theLordOfRings, firstEventFirstDate, admin,
                Stream.of(12L, 21L, 22L, 23L, 24L, 25L, 26L, 27L, 28L, 29L).collect(Collectors.toSet()));
        bookingService.bookTickets(tickets);

        User user = userService.getById(admin.getId());
        long amountLuckyWinners = user.getTickets().stream()
                .filter(ticket -> ticket.getDiscountType().equals(DiscountType.LUCKY_WINNER))
                .count();
        assertTrue(amountLuckyWinners > 0);
    }
}
