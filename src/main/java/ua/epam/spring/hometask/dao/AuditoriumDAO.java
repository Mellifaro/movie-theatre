package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.dto.Seat;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Viktor Skapoushchenko
 */
public interface AuditoriumDAO{

    Optional<Auditorium> getByName(String name) throws IOException;

    @Nonnull Collection<Auditorium> getAll() throws IOException;

    List<List<Seat>> getSeatMatrix(String auditoriumName) throws IOException;
}
