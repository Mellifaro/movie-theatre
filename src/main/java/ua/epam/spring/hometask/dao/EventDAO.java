package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Collection;

public interface EventDAO extends BaseDAO<Event> {

    Event getByName(String name);

    @Nonnull Collection<Event> getForDateRange(LocalDateTime from, LocalDateTime to);
}
