package ua.epam.spring.hometask.dao;

import org.springframework.lang.NonNull;
import ua.epam.spring.hometask.domain.EventInfo;

import java.util.Optional;

public interface EventInfoDao {

    Optional<EventInfo> getByEventId(@NonNull Long eventId);

    EventInfo save(@NonNull EventInfo eventInfo);

    void remove(@NonNull Long eventId);
}
