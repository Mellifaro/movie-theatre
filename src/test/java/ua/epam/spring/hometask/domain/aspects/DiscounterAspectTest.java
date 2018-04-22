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
import ua.epam.spring.hometask.dao.TotalDiscountInfoDAO;
import ua.epam.spring.hometask.dao.UserDiscountInfoDAO;
import ua.epam.spring.hometask.domain.DiscountType;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.TotalDiscountInfo;
import ua.epam.spring.hometask.domain.UserDiscountInfo;
import ua.epam.spring.hometask.service.booking.BookingService;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static ua.epam.spring.hometask.domain.testdata.TestData.admin;
import static ua.epam.spring.hometask.domain.testdata.TestData.firstEventFirstDate;
import static ua.epam.spring.hometask.domain.testdata.TestData.theLordOfRings;

/**
 * Created by Viktor Skapoushchenko on 4/22/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populate_db.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ContextConfiguration(classes = AppConfig.class)
public class DiscounterAspectTest {

    @Autowired
    private LuckyWinnerAspect luckyWinnerAspect;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserDiscountInfoDAO userDiscountInfoDAO;

    @Autowired
    private TotalDiscountInfoDAO totalDiscountInfoDAO;

    @Before
    public void init(){
        luckyWinnerAspect.setLuckyWinnerChance(1);
    }

    @Test
    public void testCountersForUserWorkProperly(){
        UserDiscountInfo beforeSavingForUser = userDiscountInfoDAO.getByUserId(admin.getId()).orElse(new UserDiscountInfo(100L));
        Map<DiscountType, Integer> discountMap = beforeSavingForUser.getDiscountMap();
        assertEquals(0, (int) discountMap.get(DiscountType.BIRTHDAY));
        assertEquals(0, (int) discountMap.get(DiscountType.TENTH_TICKET));

        Set<Ticket> tickets = bookingService.getTicketsFromSeats(theLordOfRings, firstEventFirstDate, admin,
                Stream.of(12L, 21L, 22L, 23L, 24L, 25L, 26L, 27L, 28L, 29L, 30L).collect(Collectors.toSet()));
        bookingService.bookTickets(tickets);

        UserDiscountInfo afterSavingForUser = userDiscountInfoDAO.getByUserId(admin.getId()).orElse(new UserDiscountInfo(100L));
        Map<DiscountType, Integer> afterDiscountMap = afterSavingForUser.getDiscountMap();
        assertEquals(0, (int) afterDiscountMap.get(DiscountType.BIRTHDAY));
        assertEquals(1, (int) afterDiscountMap.get(DiscountType.TENTH_TICKET));
    }

    @Test
    public void testCountersTotalWorkProperly(){
        TotalDiscountInfo beforeTotalDiscountInfoBirthday = totalDiscountInfoDAO.getByDiscountName(DiscountType.BIRTHDAY.toString())
                                                                          .orElse(new TotalDiscountInfo(DiscountType.BIRTHDAY, 0));
        TotalDiscountInfo beforeTotalDiscountInfoTenthTicket = totalDiscountInfoDAO.getByDiscountName(DiscountType.TENTH_TICKET.toString())
                .orElse(new TotalDiscountInfo(DiscountType.TENTH_TICKET, 0));
        assertEquals(0, (int)beforeTotalDiscountInfoBirthday.getAmount());
        assertEquals(0, (int)beforeTotalDiscountInfoTenthTicket.getAmount());

        Set<Ticket> tickets = bookingService.getTicketsFromSeats(theLordOfRings, firstEventFirstDate, admin,
                Stream.of(12L, 21L, 22L, 23L, 24L, 25L, 26L, 27L, 28L, 29L, 30L).collect(Collectors.toSet()));
        bookingService.bookTickets(tickets);

        TotalDiscountInfo afterTotalDiscountInfoBirthday = totalDiscountInfoDAO.getByDiscountName(DiscountType.BIRTHDAY.toString())
                .orElse(new TotalDiscountInfo(DiscountType.BIRTHDAY, 0));
        TotalDiscountInfo afterTotalDiscountInfoTenthTicket = totalDiscountInfoDAO.getByDiscountName(DiscountType.TENTH_TICKET.toString())
                .orElse(new TotalDiscountInfo(DiscountType.TENTH_TICKET, 0));

        assertEquals(0, (int)afterTotalDiscountInfoBirthday.getAmount());
        assertEquals(1, (int)afterTotalDiscountInfoTenthTicket.getAmount());
    }
}
