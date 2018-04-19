package ua.epam.spring.hometask.domain.services.jdbc.strategies;

import org.junit.Before;
import org.junit.Test;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.dicount.TenTicketsStrategy;

import java.time.LocalDate;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static ua.epam.spring.hometask.domain.testdata.TestData.*;

public class TenTicketsStrategyTest {

    private TenTicketsStrategy strategy;

    private Set<Ticket> tickets;
    private User testUser;

    @Before
    public void init(){
        strategy = new TenTicketsStrategy();

        testUser = new User("Ivan", "Ivanov", "ivanov@gmail.com", LocalDate.of(1985, 11, 10));
        Ticket usersTicket1 = new Ticket(testUser.getId(), theLordOfRings.getId(), firstEventFirstDate, 1);
        Ticket usersTicket2 = new Ticket(testUser.getId(), theLordOfRings.getId(), firstEventFirstDate, 2);

        NavigableSet<Ticket> purchasedTickets = testUser.getTickets();
        purchasedTickets.add(usersTicket1);
        purchasedTickets.add(usersTicket2);
        testUser.setTickets(purchasedTickets);

        tickets = new TreeSet<>();
        IntStream.range(3, 22)
                .forEach(index -> {
                    Ticket addedTicket = new Ticket(testUser.getId(), theLordOfRings.getId(), firstEventFirstDate, index);
                    addedTicket.setPrice(100.00);
                    tickets.add(addedTicket);
                });
    }

    @Test
    public void testGetDiscountUserNull(){
        int expected = 3;
        int actual = strategy.getDiscount(null, theLordOfRings, tickets);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetDiscountUserPresent(){
        int expected = 5;
        int actual = strategy.getDiscount(testUser, theLordOfRings, tickets);
        assertEquals(expected, actual);
    }

    @Test
    public void testApplyUserNull(){
        Set<Ticket> changedTickets = strategy.apply(null, theLordOfRings, tickets);
        int counterLowerPrice = 0;
        int counterDiscounts = 0;
        for(Ticket ticket : changedTickets){
            if(Math.abs(ticket.getPrice() - 50.0) <= 0.01){
                counterLowerPrice++;
            }else{
                assertEquals(ticket.getPrice(), 100.00, 0.01);
            }
            if(ticket.getDiscount() == 50){
                counterDiscounts++;
            }
        }
        assertEquals(1, counterLowerPrice);
        assertEquals(1, counterDiscounts);
    }

    @Test
    public void testApplyUserPresent(){
        Set<Ticket> changedTickets = strategy.apply(testUser, theLordOfRings, tickets);
        int counterLowerPrice = 0;
        int counterDiscounts = 0;
        for(Ticket ticket : changedTickets){
            if(Math.abs(ticket.getPrice() - 50.0) <= 0.01){
                counterLowerPrice++;
            }else{
                assertEquals(ticket.getPrice(), 100.00, 0.01);
            }
            if(ticket.getDiscount() == 50){
                counterDiscounts++;
            }
        }
        assertEquals(2, counterLowerPrice);
        assertEquals(2, counterDiscounts);
    }
}
