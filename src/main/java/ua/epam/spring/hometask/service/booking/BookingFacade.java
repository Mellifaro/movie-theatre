package ua.epam.spring.hometask.service.booking;

import org.springframework.lang.NonNull;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;


public interface BookingFacade {

    void bookTickets(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats);

    @NonNull Set<Long> getAllAvailableSeatsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime);

    @NonNull Set<Long> getAvailableVIPSeats(Set<Long> availableSeats, Auditorium auditorium);

    @NonNull Set<Long> getAvailableSimpleSeats(Set<Long> availableSeats, Auditorium auditorium);
}
