package ua.epam.spring.hometask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.EventInfoDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventInfo;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.service.event.EventService;

import java.util.Set;


@Aspect
@Component
public class CounterAspect {

    private EventInfoDao eventInfoDao;
    private EventService eventService;

    @Pointcut("execution(* *.getByName(String)) && within(ua.epam.spring.hometask.service.event.EventServiceImpl)")
    private void allGetEventByNameMethods(){}

    @Pointcut("execution(* *.getTicketsPrice(..)) && within(ua.epam.spring.hometask.service.booking.BookingServiceImpl)")
    private void allGetTicketsPriceMethods(){}

    @Pointcut("execution(* *.bookTickets(..)) && within(ua.epam.spring.hometask.service.booking.BookingServiceImpl)")
    private void allBookTicketsMethods(){}

    @AfterReturning(pointcut = "allGetEventByNameMethods()", returning = "event")
    public void incrementCountAccessByName(Event event){
        EventInfo eventInfo = eventInfoDao.getByEventId(event.getId()).orElse(new EventInfo(event.getId()));
        eventInfo.incrementCountAccessByName();
        eventInfoDao.save(eventInfo);
    }

    @AfterReturning(pointcut = "allGetTicketsPriceMethods()")
    public void incrementCountPricesWereQueried(JoinPoint joinPoint){
        Set<Ticket> tickets = (Set<Ticket>)joinPoint.getArgs()[0];
        if(!tickets.isEmpty()){
            Event event = eventService.getById(tickets.iterator().next().getEventId());
            EventInfo eventInfo = eventInfoDao.getByEventId(event.getId()).orElse(new EventInfo(event.getId()));
            eventInfo.incrementCountPricesWereQueried();
            eventInfoDao.save(eventInfo);
        }
    }

    @AfterReturning(pointcut = "allBookTicketsMethods()")
    public void incrementCountTicketsWereBooked(JoinPoint joinPoint){
        Set<Ticket> tickets = (Set<Ticket>)joinPoint.getArgs()[0];
        if(!tickets.isEmpty()) {
            Event event = eventService.getById(tickets.iterator().next().getEventId());
            EventInfo eventInfo = eventInfoDao.getByEventId(event.getId()).orElse(new EventInfo(event.getId()));
            eventInfo.incrementCountTicketsWereBooked();
            eventInfoDao.save(eventInfo);
        }
    }

    @Autowired
    public void setEventInfoDao(EventInfoDao eventInfoDao) {
        this.eventInfoDao = eventInfoDao;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
