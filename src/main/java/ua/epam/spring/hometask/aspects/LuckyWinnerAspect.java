package ua.epam.spring.hometask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.DiscountType;
import ua.epam.spring.hometask.domain.Ticket;

import java.util.Random;
import java.util.Set;

@Aspect
@Component
public class LuckyWinnerAspect {
    private static final Integer LUCKY_WINNER_CHANCE = 500;

    @Before("execution(* *.bookTickets(..))&& within(ua.epam.spring.hometask.service.booking.BookingServiceImpl)")
    public void checkLucky(JoinPoint joinPoint){
        Set<Ticket> tickets = (Set<Ticket>) joinPoint.getArgs()[0];
        tickets.forEach(this :: checkLuckyForOneTicket);
    }

    private void checkLuckyForOneTicket(Ticket ticket){
        int result = new Random().nextInt(LUCKY_WINNER_CHANCE);
        if(LUCKY_WINNER_CHANCE.equals(result)){
            ticket.setPrice(0.00);
            ticket.setDiscount(100);
            ticket.setDiscountType(DiscountType.LUCKY_WINNER);
        }
    }
}
