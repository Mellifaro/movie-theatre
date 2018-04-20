package ua.epam.spring.hometask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.Event;

import java.util.Map;
import java.util.TreeMap;

@Aspect
@Component
public class CounterAspect {
    public Map<Long, EventInfo> eventInfoMap = new TreeMap<>();

    @Pointcut("execution(* *.getByName(String)) && within(ua.epam.spring.hometask.service.event.EventServiceImpl)")
    private void allGetEventByNameMethods(){}

    @Pointcut("execution(* *.getTicketsPrice(..)) && within(ua.epam.spring.hometask.service.booking.BookingServiceImpl)")
    private void allGetTicketsPriceMethods(){}

    @Pointcut("execution(* *.bookTickets(..)) && within(ua.epam.spring.hometask.service.booking.BookingServiceImpl)")
    private void allBookTicketsMethods(){}

    @AfterReturning(pointcut = "allGetEventByNameMethods()", returning = "event")
    public void incrementCountAccessByName(Event event){
        EventInfo eventInfo = insertOrGetEventInfoFromMap(event);
        eventInfo.incrementCountAccessByName();
    }

    //todo test method
    @AfterReturning(pointcut = "allGetTicketsPriceMethods()")
    public void incrementCountPricesWereQueried(JoinPoint joinPoint){
//        Event event = (Event)joinPoint.getArgs()[0];
//        EventInfo eventInfo = insertOrGetEventInfoFromMap(event);
//        eventInfo.incrementCountPricesWereQueried();
    }

    //todo test method
    @AfterReturning(pointcut = "allBookTicketsMethods()")
    public void incrementCountTicketsWereBooked(JoinPoint joinPoint){
//        Event event = (Event)joinPoint.getArgs()[0];
//        EventInfo eventInfo = insertOrGetEventInfoFromMap(event);
//        eventInfo.incrementCountTicketsWereBooked();
    }

    private EventInfo insertOrGetEventInfoFromMap(Event event){
        Long eventId = event.getId();
        EventInfo eventInfo = eventInfoMap.get(eventId);
        if(eventInfo == null){
            eventInfo = new EventInfo(eventId);
            eventInfoMap.put(eventId, eventInfo);
        }
        return eventInfo;
    }

}
