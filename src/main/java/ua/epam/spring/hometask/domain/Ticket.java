package ua.epam.spring.hometask.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Viktor Skapoushchenko
 */
public class Ticket extends DomainObject implements Comparable<Ticket> {

    private Long userId;
    private Long eventId;
    private LocalDateTime dateTime;
    private long seat;
    private double price;
    private int discount;
    private DiscountType discountType;
    private LocalDateTime bookingDateTime;

    public Ticket() {
    }

    public Ticket(Long userId, Long eventId, LocalDateTime dateTime, long seat) {
        this.userId = userId;
        this.eventId = eventId;
        this.dateTime = dateTime;
        this.seat = seat;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public long getSeat() {
        return seat;
    }

    public void setSeat(long seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return seat == ticket.seat &&
                Objects.equals(eventId, ticket.eventId) &&
                Objects.equals(dateTime, ticket.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, dateTime, seat);
    }

    @Override
    public int compareTo(Ticket other) {
        if (other == null) {
            return 1;
        }
        int result = dateTime.compareTo(other.getDateTime());

        if (result == 0) {
            result = Long.compare(eventId, other.getEventId());
        }
        if (result == 0) {
            result = Long.compare(seat, other.getSeat());
        }
        return result;
    }

}
