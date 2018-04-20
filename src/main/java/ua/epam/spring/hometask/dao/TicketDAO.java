package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Ticket;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.NavigableSet;
import java.util.Set;

/**
 * @author Viktor Skapoushchenko
 */
public interface TicketDAO extends BaseDAO<Ticket> {

    @Nonnull
    NavigableSet<Ticket> getTicketsByUserId(Long userId);

    @Nonnull
    Set<Ticket> getPurchasedTicketsForEvent(Long eventId, LocalDateTime dateTime);
}
