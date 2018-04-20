package ua.epam.spring.hometask.service.booking;

import java.time.LocalDateTime;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

/**
 * @author Viktor Skapoushchenko
 */
public interface BookingService {

    /**
     * Getting price for the set of tickets
     * @param tickets Set of tickets that user wants to buy
     * @return total price
     */
    double getTicketsPrice(@Nonnull Set<Ticket> tickets);

    /**
     * Books tickets in internal system. If user is not
     * <code>null</code> in a ticket then booked tickets are saved with it
     * @param tickets Set of tickets
     */
    void bookTickets(@Nonnull Set<Ticket> tickets);

    /**
     * Getting all purchased tickets for event on specific air date and time
     * @param event Event to get tickets for
     * @param dateTime Date and time of airing of event
     * @return set of all purchased tickets
     */
    @Nonnull Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime);

    /**
     *
     * @param event Event to get tickets for
     * @param dateTime Date and time of airing of event
     * @param user User to buy tickets for
     * @param seats Seats for tickets
     * @return set of tickets
     */
    @Nonnull Set<Ticket> getTicketsFromSeats(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats);
}
