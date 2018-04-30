package ua.epam.spring.hometask.domain.services.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.domain.testdata.TestData;
import ua.epam.spring.hometask.exceptions.NotFoundException;
import ua.epam.spring.hometask.service.user.UserServiceImpl;

import java.time.LocalDate;
import java.util.Collection;
import java.util.NavigableSet;
import java.util.TreeSet;

import static org.junit.Assert.*;
import static ua.epam.spring.hometask.domain.testdata.TestData.adminTicket;
import static ua.epam.spring.hometask.domain.testdata.TestData.firstEventFirstDate;
import static ua.epam.spring.hometask.domain.testdata.TestData.theLordOfRings;

@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populate_db.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ContextConfiguration(classes = AppConfig.class)
public class JdbcUserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void testGetByIdSuccess(){
        User user = userService.getById(100L);
        assertEquals(TestData.admin, user);
        assertEquals(1, user.getTickets().size());
    }

    @Test(expected = NotFoundException.class)
    public void testGetByIdNotFoundFail(){
        User user = userService.getById(1L);
    }

    @Test
    public void testGetUserByEmailSuccess(){
        User user = userService.getUserByEmail("kravchukivan@gmail.com");
        assertEquals(TestData.admin, user);
        assertEquals(1, user.getTickets().size());
    }

    @Test(expected = NotFoundException.class)
    public void testGetUserByEmailNotFoundFail(){
        User user = userService.getUserByEmail("test@i.ua");
    }

    @Test
    public void testGetAllSuccess(){
        Collection<User> users = userService.getAll();
        assertEquals(2, users.size());
        assertTrue(users.contains(TestData.admin));
        assertTrue(users.contains(TestData.user));
    }

    @Test(expected = NullPointerException.class)
    public void testSaveNullUserFail(){
        userService.save(null);
    }

    @Test
    public void testSaveUserSuccess(){
        User testUser = new User("Oleg", "Petrenko", "petrenko@gmail.com", LocalDate.of(1987, 11, 11));
        testUser.setPassword("$2a$04$7Vz1Ets3LK50fTy0GZNE6.kHfG7q9SEje..ZNx13ojdsp6n44Jm..");
        userService.save(testUser);
        Collection<User> users = userService.getAll();
        assertEquals(3, users.size());
        assertTrue(users.contains(testUser));
    }

    @Test(expected = DuplicateKeyException.class)
    public void testSaveUserWithDuplicatedEmailFail(){
        User testUser = new User("Oleg", "Petrenko", "kravchukivan@gmail.com", LocalDate.of(1987, 11, 11));
        testUser.setPassword("$2a$04$7Vz1Ets3LK50fTy0GZNE6.kHfG7q9SEje..ZNx13ojdsp6n44Jm..");
        userService.save(testUser);
    }

    @Test
    public void testUpdateUserSuccess(){
        User testUser = new User("Oleg", "Petrenko", "petrenko@gmail.com", LocalDate.of(1987, 11, 11));
        testUser.setId(100L);
        testUser.setPassword("$2a$04$7Vz1Ets3LK50fTy0GZNE6.kHfG7q9SEje..ZNx13ojdsp6n44Jm..");
        userService.save(testUser);

        Collection<User> users = userService.getAll();
        assertEquals(2, users.size());
        assertTrue(users.contains(testUser));
        assertFalse(users.contains(TestData.admin));
    }

    @Test(expected = DuplicateKeyException.class)
    public void testUpdateUserWithDuplicatedEmailFail(){
        User testUser = new User("Oleg", "Petrenko", "kravchukivan@gmail.com", LocalDate.of(1987, 11, 11));
        testUser.setId(101L);
        testUser.setPassword("$2a$04$7Vz1Ets3LK50fTy0GZNE6.kHfG7q9SEje..ZNx13ojdsp6n44Jm..");
        userService.save(testUser);
    }

    @Test
    public void testSaveUserWithTickets(){
        User testUser = new User("Oleg", "Petrenko", "petrenko@gmail.com", LocalDate.of(1987, 11, 11));
        testUser.setPassword("$2a$04$7Vz1Ets3LK50fTy0GZNE6.kHfG7q9SEje..ZNx13ojdsp6n44Jm..");
        userService.save(testUser);
        User foundTestUser = userService.getUserByEmail("petrenko@gmail.com");
        Ticket ticket1 = new Ticket(foundTestUser.getId(), theLordOfRings.getId(), firstEventFirstDate, 2L);
        Ticket ticket2 = new Ticket(foundTestUser.getId(), theLordOfRings.getId(), firstEventFirstDate, 3L);

        foundTestUser.setTickets(new TreeSet<Ticket>(){{
            add(ticket1);
            add(ticket2);
        }});

        userService.save(foundTestUser);
        User receivedUser = userService.getUserByEmail("petrenko@gmail.com");
        NavigableSet<Ticket> receivedTickets = receivedUser.getTickets();
        assertEquals(foundTestUser, receivedUser);
        foundTestUser.getTickets().forEach(ticket -> assertTrue(receivedTickets.contains(ticket)));
    }

    @Test
    public void testUpdateUserTicketsSuccess(){
        User user = userService.getById(100L);
        assertEquals(1, user.getTickets().size());

        user.getTickets().remove(adminTicket);
        userService.save(user);

        User changedUser = userService.getById(100L);
        assertEquals(0, changedUser.getTickets().size());
    }
}
