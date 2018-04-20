package ua.epam.spring.hometask.domain.services.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.config.AppConfig;

import static org.junit.Assert.*;
import static ua.epam.spring.hometask.domain.testdata.TestData.*;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.exceptions.NotFoundException;
import ua.epam.spring.hometask.service.event.EventService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Viktor_Skapoushchenk on 4/19/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populate_db.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ContextConfiguration(classes = AppConfig.class)
public class JdbcEventServiceTest {

    @Autowired
    private EventService eventService;

    @Test
    public void testGetById(){
        Event event = eventService.getById(100L);
        assertEquals(event, theLordOfRings);
    }

    @Test
    public void testGetByName(){
        Event event = eventService.getByName("The Lord Of The Rings");
        assertEquals(event, theLordOfRings);
    }

    @Test(expected = NullPointerException.class)
    public void testGetByIdNull(){
        Event event = eventService.getById(null);
    }

    @Test(expected = NullPointerException.class)
    public void testGetByNameNull(){
        Event event = eventService.getByName(null);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotExistingEventById(){
        Event event = eventService.getById(1L);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotExistingEventByName(){
        Event event = eventService.getByName("My Test");
    }

    @Test
    public void testGetAll(){
        Collection<Event> events = eventService.getAll();
        assertEquals(2, events.size());
        assertTrue(events.contains(theLordOfRings));
        assertTrue(events.contains(scaryMovie));
    }

    @Test
    public void testRemoveEvent(){
        Collection<Event> beforeRemoving = eventService.getAll();
        eventService.remove(scaryMovie);
        Collection<Event> afterRemoving = eventService.getAll();

        assertEquals(beforeRemoving.size() - 1, afterRemoving.size());
        assertTrue(beforeRemoving.contains(scaryMovie));
        assertFalse(afterRemoving.contains(scaryMovie));
    }

    @Test
    public void testSaveNewEvent(){
        Event event = new Event("test", 25.00, EventRating.HIGH);
        event.addAirDateTime(LocalDateTime.of(2019, 11, 10, 10, 30));
        event.addAirDateTime(LocalDateTime.of(2019, 11, 10, 10, 30), redAuditorium);
        eventService.save(event);
        assertEquals(event, eventService.getByName("test"));
    }

    @Test
    public void testUpdateExistingEvent(){
        Event event = new Event("test", 25.00, EventRating.HIGH);
        event = eventService.save(event);

        event.setRating(EventRating.LOW);
        event.setBasePrice(30.00);
        event.addAirDateTime(LocalDateTime.of(2019, 11, 10, 10, 15), redAuditorium);
        eventService.save(event);

        Event result = eventService.getByName("test");
        assertEquals(EventRating.LOW, result.getRating());
        assertEquals(30.00, result.getBasePrice(), 0.01);
        assertEquals(1, result.getAirDates().size());
    }
}
