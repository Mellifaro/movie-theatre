package ua.epam.spring.hometask.domain;

public class EventInfo {
    private Long eventId;
    private int countByName;
    private int countPriceQueried;
    private int countTicketsBooked;

    public EventInfo() {
    }

    public EventInfo(Long eventId) {
        this.eventId = eventId;
    }

    public int incrementCountAccessByName(){
        return ++countByName;
    }

    public int incrementCountPricesWereQueried(){
        return ++countPriceQueried;
    }

    public int incrementCountTicketsWereBooked(){
        return ++countTicketsBooked;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public int getCountByName() {
        return countByName;
    }

    public void setCountByName(int countByName) {
        this.countByName = countByName;
    }

    public int getCountPriceQueried() {
        return countPriceQueried;
    }

    public void setCountPriceQueried(int countPriceQueried) {
        this.countPriceQueried = countPriceQueried;
    }

    public int getCountTicketsBooked() {
        return countTicketsBooked;
    }

    public void setCountTicketsBooked(int countTicketsBooked) {
        this.countTicketsBooked = countTicketsBooked;
    }
}
