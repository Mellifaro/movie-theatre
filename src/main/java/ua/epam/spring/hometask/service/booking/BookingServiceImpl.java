package ua.epam.spring.hometask.service.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.dao.TicketDAO;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.service.auditorium.AuditoriumService;
import ua.epam.spring.hometask.service.dicount.DiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author Viktor Skapoushchenko
 */
@Service
public class BookingServiceImpl implements BookingService {
    private static final Double LOW_MULTIPLIER = 0.8;
    private static final Double MID_MULTIPLIER = 1.0;
    private static final Double HIGH_MULTIPLIER = 1.2;

    private static final Double VIP_SEAT_MULTIPLIER = 2.0;

    private DiscountService discountService;
    private AuditoriumService auditoriumService;
    private TicketDAO ticketDAO;

    @Autowired
    public BookingServiceImpl(DiscountService discountService, TicketDAO ticketDAO) {
        this.discountService = discountService;
        this.ticketDAO = ticketDAO;
    }

    @Override
    public double getTicketsPrice(@Nonnull Set<Ticket> tickets) {
        return tickets.stream().mapToDouble(Ticket::getPrice).sum();
    }

    @Override
    //todo rewrite with jdbc batch insert
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        tickets.forEach(ticketDAO::save);
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return ticketDAO.getPurchasedTicketsForEvent(event.getId(), dateTime);
    }

    @Nonnull
    @Override
    public Set<Ticket> getTicketsFromSeats(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats){
        Set<Long> busySeats = checkForSeatsAvailable(event, dateTime, seats);
        if(!busySeats.isEmpty()){
            throw new IllegalStateException("Seats: " + busySeats + " are already purchased");
        }

        Set<Ticket> tickets = seats.stream().map(seat -> {
            Long userId = user == null ? null : user.getId();
            Ticket ticket = new Ticket(userId, event.getId(), dateTime, seat);
            ticket.setPrice(event.getBasePrice());
            return ticket;
        }).collect(Collectors.toSet());

        applyEventRatingPriceChanging(event, tickets);
        applyVIPSeatPriceChanging(event, dateTime, tickets);
        discountService.applyDiscountsToTickets(user, event, tickets);
        return tickets;
    }

    private Set<Ticket> applyVIPSeatPriceChanging(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nonnull Set<Ticket> tickets){
        Auditorium auditorium = event.getAuditoriums().get(dateTime);
        tickets.forEach(ticket -> {
            if(auditorium.hasVipSeat(ticket.getSeat())){
                ticket.setPrice(ticket.getPrice() * VIP_SEAT_MULTIPLIER);
            }
        });
        return tickets;
    }

    private Set<Ticket> applyEventRatingPriceChanging(@Nonnull Event event, @Nonnull Set<Ticket> tickets){
        tickets.forEach(ticket -> {
            double price = ticket.getPrice();
            switch (event.getRating()){
                case LOW:
                    price = price * LOW_MULTIPLIER;
                    break;
                case MID:
                    price = price * MID_MULTIPLIER;
                    break;
                case HIGH:
                    price = price * HIGH_MULTIPLIER;
                    break;
            }
            ticket.setPrice(price);
        });
        return tickets;
    }

    private Set<Long> checkForSeatsAvailable(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nonnull Set<Long> seats) {
        Set<Ticket> purchasedTickets = getPurchasedTicketsForEvent(event, dateTime);
        Set<Long> busySeats = new TreeSet<>();
        seats.forEach(seat -> {
            purchasedTickets.forEach(purchasedTicket -> {
                if(seat.equals(purchasedTicket.getSeat())){
                    busySeats.add(seat);
                }
            });
        });
        return busySeats;
    }
}
