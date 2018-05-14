package ua.epam.spring.hometask.service.auditorium;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.dto.AuditoriumDTO;

/**
 * @author Viktor Skapoushchenko
 */
public interface AuditoriumService {

    /**
     * Getting all auditoriums from the system
     * @return set of all auditoriums
     */
    @Nonnull Collection<Auditorium> getAll() throws IOException;

    /**
     * Finding auditorium by name
     * @param name Name of the auditorium
     * @return found auditorium or <code>null</code>
     */
    @Nullable Auditorium getByName(@Nonnull String name) throws IOException;

    AuditoriumDTO getWithPurchasedSeats(Auditorium auditorium, Set<Ticket> purchasedTickets) throws IOException;
}
