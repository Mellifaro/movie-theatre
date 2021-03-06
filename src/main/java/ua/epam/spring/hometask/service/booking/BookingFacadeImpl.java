package ua.epam.spring.hometask.service.booking;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.payment.PaymentService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class BookingFacadeImpl implements BookingFacade{

    private BookingService bookingService;
    private PaymentService paymentService;

    public BookingFacadeImpl(BookingService bookingService, PaymentService paymentService) {
        this.bookingService = bookingService;
        this.paymentService = paymentService;
    }

    @Override
    @Transactional
    public Set<Ticket> bookTickets(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        Set<Ticket> tickets = bookingService.getTicketsFromSeats(event, dateTime, user, seats);
        Double price = bookingService.getTicketsPrice(tickets);
        bookingService.bookTickets(tickets);
        user.getTickets().addAll(tickets);
        paymentService.withdrawMoney(user, price);
        return tickets;
    }

    @Override
    @Transactional
    public Set<Long> getAllAvailableSeatsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return bookingService.getAllAvailableSeatsForEvent(event, dateTime);
    }

    @Override
    public Set<Long> getAvailableVIPSeats(Set<Long> availableSeats, Auditorium auditorium) {
        return bookingService.getAvailableVIPSeats(availableSeats, auditorium);
    }

    @Override
    public Set<Long> getAvailableSimpleSeats(Set<Long> availableSeats, Auditorium auditorium) {
        return bookingService.getAvailableSimpleSeats(availableSeats, auditorium);
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return bookingService.getPurchasedTicketsForEvent(event, dateTime);
    }
}
