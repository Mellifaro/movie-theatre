package ua.epam.spring.hometask.service.event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.AbstractDomainObjectService;

/**
 * @author Viktor Skapoushchenko
 */
public interface EventService extends AbstractDomainObjectService<Event> {

    /**
     * Finding event by name
     * @param name Name of the event
     * @return found event or <code>null</code>
     */
    @Nullable Event getByName(@Nonnull String name);
}
