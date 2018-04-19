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
import ua.epam.spring.hometask.exceptions.NotFoundException;
import ua.epam.spring.hometask.service.event.EventService;
import ua.epam.spring.hometask.service.event.EventServiceImpl;

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

    @Test(expected = IllegalArgumentException.class)
    public void testGetByIdNull(){
        Event event = eventService.getById(null);
    }

    @Test(expected = IllegalArgumentException.class)
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
}
