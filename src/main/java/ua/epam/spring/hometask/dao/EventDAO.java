package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

/**
 * @author Viktor Skapoushchenko
 */
public interface EventDAO extends BaseDAO<Event> {

    Optional<Event> getByName(String name);

    @Nonnull Collection<Event> getForDateRange(LocalDateTime from, LocalDateTime to);
}
