package ua.epam.spring.hometask.domain.services.jdbc.strategies;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.dicount.DiscountServiceImpl;

import java.time.LocalDate;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

import static ua.epam.spring.hometask.domain.testdata.TestData.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DiscountServiceImplTest {

    @Autowired
    private DiscountServiceImpl discountService;

    private Set<Ticket> tickets;
    private User testUser;

    @Before
    public void init(){
        testUser = new User("Ivan", "Ivanov", "ivanov@gmail.com", LocalDate.of(1985, 11, 10));
        Ticket usersTicket1 = new Ticket(testUser.getId(), theLordOfRings.getId(), firstEventFirstDate, 1);
        Ticket usersTicket2 = new Ticket(testUser.getId(), theLordOfRings.getId(), firstEventFirstDate, 2);

        NavigableSet<Ticket> purchasedTickets = testUser.getTickets();
        purchasedTickets.add(usersTicket1);
        purchasedTickets.add(usersTicket2);
        testUser.setTickets(purchasedTickets);

        tickets = new TreeSet<>();
        IntStream.range(3, 18)
                .forEach(index -> {
                    Ticket addedTicket = new Ticket(testUser.getId(), theLordOfRings.getId(), firstEventFirstDate, index);
                    addedTicket.setPrice(100.00);
                    tickets.add(addedTicket);
                });
    }

    @Test
    public void testBirthdayStrategyWin(){
        testUser.setBirthday(LocalDate.now());
        Set<Ticket> changedTickets = discountService.applyDiscountsToTickets(testUser, theLordOfRings, tickets);

        changedTickets.forEach(ticket -> {
            assertEquals(95, ticket.getPrice(), 0.01);
            assertEquals(5, ticket.getDiscount());
        });
    }

    @Test
    public void testTenTicketsStrategyWin(){
        testUser.setBirthday(LocalDate.now().minusDays(10));
        Set<Ticket> changedTickets = discountService.applyDiscountsToTickets(testUser, theLordOfRings, tickets);

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
}
