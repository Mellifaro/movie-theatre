package ua.epam.spring.hometask.dto;

import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.DiscountType;
import ua.epam.spring.hometask.domain.Event;

import java.util.Objects;

public class TicketDTO {
    private Long id;
    private Long userId;
    private Event event;
    private String dateTime;
    private Auditorium auditorium;
    private long seat;
    private double price;
    private int discount;
    private DiscountType discountType;
    private String bookingDateTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
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

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public String getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(String bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketDTO ticketDTO = (TicketDTO) o;
        return seat == ticketDTO.seat &&
                Objects.equals(event, ticketDTO.event) &&
                Objects.equals(dateTime, ticketDTO.dateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(event, dateTime, seat);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                ",\n userId: " + userId +
                ",\n eventName: " + event.getName() +
                ",\n dateTime: \'" + dateTime + '\'' +
                ",\n auditorium: " + auditorium.getName() +
                ",\n seat: " + seat +
                ",\n price: " + price +
                ",\n discount: " + discount +
                ",\n discountType: " + discountType +
                ",\n bookingDateTime: \'" + bookingDateTime + '\'' +
                "\n}";
    }
}
