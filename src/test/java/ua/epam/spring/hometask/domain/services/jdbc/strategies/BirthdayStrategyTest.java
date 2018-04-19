package ua.epam.spring.hometask.domain.services.jdbc.strategies;

import org.junit.Before;
import org.junit.Test;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.dicount.BirthdayStrategy;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static ua.epam.spring.hometask.domain.testdata.TestData.*;

public class BirthdayStrategyTest {

    private BirthdayStrategy strategy;

    @Before
    public void init(){
        strategy = new BirthdayStrategy();
        adminTicket.setPrice(100.00);
        adminTicket.setDiscount(0);
    }

    @Test
    public void testGetDiscountIfUserIsNull(){
        Set<Ticket> tickets = new HashSet<>();
        tickets.add(adminTicket);
        int discount = strategy.getDiscount(null, theLordOfRings, tickets);
        assertEquals(0, discount);
    }

    @Test
    public void testGetDiscountIfUsersBirthdayIsBeforeRange(){
        User testUser = new User();
        testUser.setBirthday(LocalDate.now().minusDays(6));

        Set<Ticket> tickets = new HashSet<>();
        tickets.add(adminTicket);
        int discount = strategy.getDiscount(testUser, theLordOfRings, tickets);
        assertEquals(0, discount);
    }

    @Test
    public void testGetDiscountIfUsersBirthdayIsAfterRange(){
        User testUser = new User();
        testUser.setBirthday(LocalDate.now().plusDays(6));

        Set<Ticket> tickets = new HashSet<>();
        tickets.add(adminTicket);
        int discount = strategy.getDiscount(testUser, theLordOfRings, tickets);
        assertEquals(0, discount);
    }

    @Test
    public void testGetDiscountIfUsersBirthdayIsInRange(){
        User testUser = new User();
        testUser.setBirthday(LocalDate.now().plusDays(4));

        Set<Ticket> tickets = new HashSet<>();
        tickets.add(adminTicket);
        int discount = strategy.getDiscount(testUser, theLordOfRings, tickets);
        assertEquals(5, discount);
    }

    @Test
    public void testApply(){
        Ticket testTicket = new Ticket();
        testTicket.setUserId(100L);
        testTicket.setPrice(100.00);

        Set<Ticket> tickets = Stream.of(adminTicket, testTicket).collect(Collectors.toSet());
        strategy.apply(admin, theLordOfRings, tickets);
        tickets.forEach(ticket -> {
            assertEquals(95.00, ticket.getPrice(), 0.1);
            assertEquals(5, ticket.getDiscount());
        });
    }

    @Test
    public void testApplyUserNull(){
        adminTicket.setPrice(100.00);
        Ticket testTicket = new Ticket();
        testTicket.setUserId(100L);
        testTicket.setPrice(100.00);

        Set<Ticket> tickets = Stream.of(adminTicket, testTicket).collect(Collectors.toSet());
        strategy.apply(null, theLordOfRings, tickets);
        tickets.forEach(ticket -> {
            assertEquals(100.00, ticket.getPrice(), 0.1);
            assertEquals(0, ticket.getDiscount());
        });
    }
}
