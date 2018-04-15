package ua.epam.spring.hometask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.DiscountType;
import ua.epam.spring.hometask.domain.Ticket;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Aspect
@Component
public class DiscounterAspect {
    private Map<DiscountType, Integer> totalDiscountsMap = new EnumMap<>(DiscountType.class);
    private Map<Long, Map<DiscountType, Integer>> discountsPerUserMap = new HashMap<>();

    @Pointcut("execution(* *.bookTickets(..))&& within(ua.epam.spring.hometask.service.booking.BookingServiceImpl)")
    private void allBookTicketsMethods(){}

    @AfterReturning(pointcut = "allBookTicketsMethods()")
    public void incrementDiscountCounters(JoinPoint joinPoint){
        Set<Ticket> tickets = (Set<Ticket>) joinPoint.getArgs()[0];
        tickets.forEach(ticket -> {
            Long userId = ticket.getUserId();
            DiscountType discountType = ticket.getDiscountType();
            if (discountType != null) {
                if (userId != null) {
                    Map<DiscountType, Integer> discounts = discountsPerUserMap.get(userId);
                    if(discounts == null){
                        discounts = new HashMap<>();
                    }
                    Integer amount = discounts.get(discountType) == null ? 0 : discounts.get(discountType);
                    discounts.put(discountType, ++amount);
                }
                Integer totalAmount = totalDiscountsMap.get(discountType) == null ? 0 : totalDiscountsMap.get(discountType);
                totalDiscountsMap.put(discountType, ++totalAmount);
            }
        });
    }
}
