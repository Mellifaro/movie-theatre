package ua.epam.spring.hometask.domain.aspects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.dao.EventInfoDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventInfo;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.service.booking.BookingService;
import ua.epam.spring.hometask.service.event.EventService;

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
public class CounterAspectTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventInfoDao eventInfoDao;

    @Test
    public void testCountGetEventByName(){
        EventInfo eventInfo = eventInfoDao.getByEventId(theLordOfRings.getId()).orElse(new EventInfo(theLordOfRings.getId()));
        assertEquals(0, eventInfo.getCountByName());
        Event event = eventService.getByName("The Lord Of The Rings");

        EventInfo result = eventInfoDao.getByEventId(theLordOfRings.getId()).orElse(new EventInfo(theLordOfRings.getId()));
        assertEquals(1, result.getCountByName());
        assertEquals(0, result.getCountPriceQueried());
        assertEquals(0, result.getCountTicketsBooked());
    }

    @Test
    public void testGetByNameInsert(){
        EventInfo eventInfo = eventInfoDao.getByEventId(scaryMovie.getId()).orElse(new EventInfo(scaryMovie.getId()));
        assertEquals(0, eventInfo.getCountByName());

        Event event = eventService.getByName(scaryMovie.getName());
        EventInfo result = eventInfoDao.getByEventId(scaryMovie.getId()).orElse(new EventInfo(scaryMovie.getId()));
        assertEquals(1, result.getCountByName());
        assertEquals(0, result.getCountPriceQueried());
        assertEquals(0, result.getCountTicketsBooked());
    }

    @Test
    public void testCountGetTicketsPrice(){
        Set<Ticket> tickets = bookingService.getTicketsFromSeats(theLordOfRings, firstEventFirstDate, admin,
                Stream.of(12L, 21L, 22L, 23L, 24L, 25L, 26L, 27L, 28L, 29L).collect(Collectors.toSet()));
        EventInfo eventInfo = eventInfoDao.getByEventId(theLordOfRings.getId()).orElse(new EventInfo(theLordOfRings.getId()));
        assertEquals(0, eventInfo.getCountPriceQueried());
        bookingService.getTicketsPrice(tickets);

        EventInfo result = eventInfoDao.getByEventId(theLordOfRings.getId()).orElse(new EventInfo(theLordOfRings.getId()));
        assertEquals(0, result.getCountByName());
        assertEquals(1, result.getCountPriceQueried());
        assertEquals(0, result.getCountTicketsBooked());
    }

    @Test
    public void testCountBookTickets(){
        Set<Ticket> tickets = bookingService.getTicketsFromSeats(theLordOfRings, firstEventFirstDate, admin,
                Stream.of(12L, 21L, 22L, 23L, 24L, 25L, 26L, 27L, 28L, 29L).collect(Collectors.toSet()));
        EventInfo eventInfo = eventInfoDao.getByEventId(theLordOfRings.getId()).orElse(new EventInfo(theLordOfRings.getId()));
        assertEquals(0, eventInfo.getCountPriceQueried());
        bookingService.bookTickets(tickets);

        EventInfo result = eventInfoDao.getByEventId(theLordOfRings.getId()).orElse(new EventInfo(theLordOfRings.getId()));
        assertEquals(0, result.getCountByName());
        assertEquals(0, result.getCountPriceQueried());
        assertEquals(1, result.getCountTicketsBooked());
    }
}
