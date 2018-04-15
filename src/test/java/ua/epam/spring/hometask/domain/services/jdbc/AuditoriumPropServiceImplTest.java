package ua.epam.spring.hometask.domain.services.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.testdata.TestData;
import ua.epam.spring.hometask.service.auditorium.AuditoriumService;

import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class AuditoriumPropServiceImplTest {

    @Autowired
    private AuditoriumService auditoriumService;

    @Test
    public void testGetAuditoriumByNameSuccess() throws IOException {
        Auditorium auditorium = auditoriumService.getByName("green");
        assertEquals(TestData.greenAuditorium, auditorium);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAuditoriumByNameNotFoundFail() throws IOException {
        Auditorium auditorium = auditoriumService.getByName("test");
        assertEquals(TestData.greenAuditorium, auditorium);
    }

    @Test
    public void testGetAllAuditoriums() throws IOException {
        Collection<Auditorium> auditoriums = auditoriumService.getAll();
        assertEquals(2, auditoriums.size());
        assertTrue(auditoriums.contains(TestData.greenAuditorium));
        assertTrue(auditoriums.contains(TestData.redAuditorium));
    }
}
