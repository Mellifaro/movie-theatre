package ua.epam.spring.hometask.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.spring.hometask.dao.EventDAO;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.exceptions.NotFoundException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDAO eventDAO;

    @Nullable
    @Override
    @Transactional
    public Event getByName(@Nonnull String name) {
        Objects.requireNonNull(name);
        return eventDAO.getByName(name).orElseThrow(() -> new NotFoundException("Event with name: " + name + "not found"));
    }

    @Override
    @Transactional
    public Event save(@Nonnull Event event) {
        Objects.requireNonNull(event);
        return eventDAO.save(event);
    }

    @Override
    @Transactional
    public void remove(@Nonnull Event event) {
        eventDAO.remove(event);
    }

    @Override
    @Transactional
    public Event getById(@Nonnull Long id) {
        Objects.requireNonNull(id);
        return eventDAO.getById(id).orElseThrow(() -> new NotFoundException("Event with id: " + id + "not found"));
    }

    @Nonnull
    @Override
    @Transactional
    public Collection<Event> getAll() {
        return eventDAO.getAll();
    }
}
