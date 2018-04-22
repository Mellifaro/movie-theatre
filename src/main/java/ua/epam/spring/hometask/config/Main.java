package ua.epam.spring.hometask.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.epam.spring.hometask.aspects.CounterAspect;
import ua.epam.spring.hometask.service.event.EventService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        UserDAO userDAO = context.getBean(UserDAO.class);
//        User user1 = userDAO.getUserByEmail("kravchukivan@gmail.com").get();
//        System.out.println(user1);
//
//        User user2 = userDAO.getById(101L).get();
//
//        User newUser = userDAO.save(new User("testName", "testLastName", "testEmail", LocalDate.now()));
//
//        Collection<User> users = userDAO.getAll();
//        User deletedUser = new User();
//        deletedUser.setEventId(102L);
//
//        userDAO.remove(deletedUser);
//        Collection<User> users2 = userDAO.getAll();
//        System.out.println(users);

//        AuditoriumDAO auditoriumDAO = context.getBean(AuditoriumDAO.class);
//        Collection<Auditorium> auditoriums = auditoriumDAO.getAll();
//        System.out.println(auditoriums);

//        EventDAO eventDAO = context.getBean(EventDAO.class);
//        Event event = eventDAO.getById(100L).get();
//        System.out.println("");
//
//        TicketDAO ticketDAO = context.getBean(TicketDAO.class);
//        Ticket ticket = ticketDAO.getById(100L).get();
//        System.out.println("");

        EventService eventService = context.getBean(EventService.class);
        CounterAspect counterAspect = context.getBean(CounterAspect.class);
        eventService.getByName("The Lord Of The Rings");

    }
}
