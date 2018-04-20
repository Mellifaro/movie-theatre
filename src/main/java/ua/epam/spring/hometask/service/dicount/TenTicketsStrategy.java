package ua.epam.spring.hometask.service.dicount;

import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.DiscountType;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

@Component
public class TenTicketsStrategy implements DiscountStrategy{

    private static final Integer DISCOUNT_FOR_TENTH_TICKET = 50;
    private static final Integer WINNER_TICKET = 10;
    private static final DiscountType DISCOUNT_TYPE = DiscountType.TENTH_TICKET;

    @Override
    public int getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull Set<Ticket> tickets) {
        int buyedTickets = user == null ? 0 : user.getTickets().size() % 10;
        if ((tickets.size() + buyedTickets) < WINNER_TICKET) {
            return 0;
        } else {
            return Math.round(((buyedTickets + tickets.size()) / WINNER_TICKET) * (float)DISCOUNT_FOR_TENTH_TICKET / tickets.size());
        }

    }

    @Override
    public Set<Ticket> apply(@Nullable User user, @Nonnull Event event, @Nonnull Set<Ticket> tickets) {
        int counter = user == null ? 0 : user.getTickets().size();
        for(Ticket ticket : tickets){
            if(++counter % WINNER_TICKET == 0){
                ticket.setDiscount(DISCOUNT_FOR_TENTH_TICKET);
                double price = ticket.getPrice() *(100 - DISCOUNT_FOR_TENTH_TICKET) / 100;
                ticket.setPrice(price);
                ticket.setDiscountType(DISCOUNT_TYPE);
            }
        }
        return tickets;
    }

    @Override
    public DiscountType getDiscountType() {
        return DISCOUNT_TYPE;
    }
}
