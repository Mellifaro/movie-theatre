package ua.epam.spring.hometask.domain.services.jdbc;

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

import static org.junit.Assert.*;
import static ua.epam.spring.hometask.domain.testdata.TestData.*;

import ua.epam.spring.hometask.dao.TicketDAO;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.service.booking.BookingService;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Viktor_Skapoushchenko on 4/20/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populate_db.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ContextConfiguration(classes = AppConfig.class)
public class JdbcBookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private LuckyWinnerAspect luckyWinnerAspect;

    @Before
    public void init(){
        luckyWinnerAspect.setLuckyWinnerChance(1);
    }

    @Test
    public void testGetTicketsPrice(){
        Set<Ticket> tickets = bookingService.getTicketsFromSeats(theLordOfRings, firstEventFirstDate, admin,
                Stream.of(12L, 21L, 22L, 23L, 24L, 25L, 26L, 27L, 28L, 29L).collect(Collectors.toSet()));
        Double finalPrice = bookingService.getTicketsPrice(tickets);
        Double basePriceForTicket = theLordOfRings.getBasePrice() * 1.2;
        Double expectedResult = basePriceForTicket * 8 + basePriceForTicket * 2 + basePriceForTicket * 0.5;
        assertEquals(expectedResult, finalPrice, 0.1);
    }

    @Test
    public void testGetPurchasedTicketsForEvent(){
        Set<Ticket> tickets1 = bookingService.getPurchasedTicketsForEvent(theLordOfRings, firstEventFirstDate);
        assertEquals(1, tickets1.size());
        assertTrue(tickets1.contains(adminTicket));

        Set<Ticket> tickets2 = bookingService.getPurchasedTicketsForEvent(theLordOfRings, firstEventSecondDate);
        assertEquals(0, tickets2.size());
    }

    @Test
    public void testBookTickets(){
        Set<Ticket> tickets = bookingService.getTicketsFromSeats(theLordOfRings, firstEventFirstDate, admin,
                Stream.of(12L, 21L, 22L, 23L, 24L, 25L, 26L, 27L, 28L, 29L).collect(Collectors.toSet()));
        bookingService.bookTickets(tickets);

        Set<Ticket> adminTickets = ticketDAO.getTicketsByUserId(admin.getId());
        assertEquals(11, adminTickets.size());
        adminTickets.forEach(ticket -> assertNotNull(ticket.getId()));
        tickets.forEach(ticket -> assertTrue(adminTickets.contains(ticket)));
    }

}
