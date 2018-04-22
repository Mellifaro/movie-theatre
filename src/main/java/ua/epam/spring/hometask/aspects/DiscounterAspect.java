package ua.epam.spring.hometask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.TotalDiscountInfoDAO;
import ua.epam.spring.hometask.dao.UserDiscountInfoDAO;
import ua.epam.spring.hometask.domain.DiscountType;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.TotalDiscountInfo;
import ua.epam.spring.hometask.domain.UserDiscountInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Aspect
@Component
public class DiscounterAspect {

    @Autowired
    private TotalDiscountInfoDAO totalDiscountInfoDAO;

    @Autowired
    private UserDiscountInfoDAO userDiscountInfoDAO;

    @Pointcut("execution(* *.bookTickets(..))&& within(ua.epam.spring.hometask.service.booking.BookingServiceImpl)")
    private void allBookTicketsMethods(){}

    @AfterReturning(pointcut = "allBookTicketsMethods()")
    public void incrementDiscountCounters(JoinPoint joinPoint){
        Set<Ticket> tickets = (Set<Ticket>) joinPoint.getArgs()[0];
        tickets.forEach(ticket -> {
            Long userId = ticket.getUserId();
            DiscountType discountType = ticket.getDiscountType();
            if (!discountType.equals(DiscountType.NONE)) {
                if (userId != null) {
                    UserDiscountInfo userDiscountInfo = userDiscountInfoDAO.getByUserId(userId).orElse(new UserDiscountInfo(userId));
                    Map<DiscountType, Integer> discounts = userDiscountInfo.getDiscountMap();
                    if(discounts == null){
                        discounts = new HashMap<>();
                    }
                    Integer amount = discounts.get(discountType) == null ? 0 : discounts.get(discountType);
                    discounts.put(discountType, ++amount);
                    userDiscountInfoDAO.save(userDiscountInfo);
                }
                TotalDiscountInfo totalDiscountInfo = totalDiscountInfoDAO.getByDiscountName(discountType.toString())
                                                                          .orElse(new TotalDiscountInfo(discountType, 0));
                totalDiscountInfo.setAmount(totalDiscountInfo.getAmount() + 1);
                totalDiscountInfoDAO.save(totalDiscountInfo);
            }
        });
    }
}
