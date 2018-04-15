package ua.epam.spring.hometask.service.dicount;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

/**
 * @author Viktor Skapoushchenko
 */
public interface DiscountService {

    /**
     * Getting all tickets with discount based on some rules for user that buys some number of
     * tickets for the specific date time of the event. Modifies tickets.
     * @param user that books tickets
     * @param tickets tickets, to which discounts must be applied
     * @return tickets with discount
     */
    Set<Ticket> applyDiscountsToTickets(@Nullable User user, @Nonnull Event event, @Nonnull Set<Ticket> tickets);

}
