package ua.epam.spring.hometask.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.dao.EventDAO;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.exceptions.NotFoundException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDAO eventDAO;

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        return eventDAO.getByName(name).orElseThrow(() -> new NotFoundException("Event with name: " + name + "not found"));
    }

    @Override
    public Event save(@Nonnull Event event) {
        return eventDAO.save(event);
    }

    @Override
    public void remove(@Nonnull Event event) {
        eventDAO.remove(event);
    }

    @Override
    public Event getById(@Nonnull Long id) {
        return eventDAO.getById(id).orElseThrow(() -> new NotFoundException("Event with id: " + id + "not found"));
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return eventDAO.getAll();
    }
}
