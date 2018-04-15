package ua.epam.spring.hometask.domain.services.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.domain.testdata.TestData;
import ua.epam.spring.hometask.service.user.UserService;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populate_db.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ContextConfiguration(classes = AppConfig.class)
public class JdbcUserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetByIdSuccess(){
        User user = userService.getById(100L);
        assertEquals(TestData.admin, user);
        assertEquals(1, user.getTickets().size());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testGetByIdNotFoundFail(){
        User user = userService.getById(1L);
    }

    @Test
    public void testGetUserByEmailSuccess(){
        User user = userService.getUserByEmail("kravchukivan@gmail.com");
        assertEquals(TestData.admin, user);
        assertEquals(1, user.getTickets().size());
    }

    @Test(expected = EmptyResultDataAccessException.class)
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

    @Test(expected = IllegalArgumentException.class)
    public void testSaveNullUserFail(){
        userService.save(null);
    }

    @Test
    public void testSaveUserSuccess(){
        User testUser = new User("Oleg", "Petrenko", "petrenko@gmail.com", LocalDate.of(1987, 11, 11));
        userService.save(testUser);
        Collection<User> users = userService.getAll();
        assertEquals(3, users.size());
        assertTrue(users.contains(testUser));
    }

    @Test(expected = DuplicateKeyException.class)
    public void testSaveUserWithDuplicatedEmailFail(){
        User testUser = new User("Oleg", "Petrenko", "kravchukivan@gmail.com", LocalDate.of(1987, 11, 11));
        userService.save(testUser);
    }

    @Test
    public void testUpdateUserSuccess(){
        User testUser = new User("Oleg", "Petrenko", "petrenko@gmail.com", LocalDate.of(1987, 11, 11));
        testUser.setId(100L);
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
        userService.save(testUser);
    }
}