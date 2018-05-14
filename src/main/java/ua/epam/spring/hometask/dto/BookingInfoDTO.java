package ua.epam.spring.hometask.dto;


import java.time.LocalDateTime;
import java.util.Set;

public class BookingInfoDTO {
    private Long eventId;
    private LocalDateTime dateTime;
    private Set<Long> seats;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Set<Long> getSeats() {
        return seats;
    }

    public void setSeats(Set<Long> seats) {
        this.seats = seats;
    }
}
