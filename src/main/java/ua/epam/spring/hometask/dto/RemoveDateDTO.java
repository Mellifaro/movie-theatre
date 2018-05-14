package ua.epam.spring.hometask.dto;

import java.time.LocalDateTime;

public class RemoveDateDTO {
    private Long eventId;
    private LocalDateTime eventTime;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }
}
