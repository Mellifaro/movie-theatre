package ua.epam.spring.hometask.service.dicount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.DiscountType;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private List<DiscountStrategy> strategies;

    @Override
    public Set<Ticket> applyDiscountsToTickets(@Nullable User user, @Nonnull Event event, @Nonnull Set<Ticket> tickets) {
        Map<DiscountType, Integer> discountMap = new EnumMap<>(DiscountType.class);
        strategies.forEach(strategy -> discountMap.put(strategy.getDiscountType(), strategy.getDiscount(user, event, tickets)));
        int maxDiscount = discountMap.values().stream().mapToInt(discount -> discount).max().getAsInt();
        if(maxDiscount != 0){
            DiscountType winnerStrategyType = discountMap.keySet().stream()
                    .filter(key -> discountMap.get(key) == maxDiscount)
                    .findFirst()
                    .get();
            DiscountStrategy winnerStrategy = strategies.stream()
                    .filter(strategy -> strategy.getDiscountType().equals(winnerStrategyType))
                    .findFirst()
                    .get();
            winnerStrategy.apply(user, event, tickets);
        }
        return tickets;
    }
}
