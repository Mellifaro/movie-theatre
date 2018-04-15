package ua.epam.spring.hometask.aspects;

public class EventInfo {
    private Long id;
    private int countAccessByName;
    private int countPricesWereQueried;
    private int countTicketsWereBooked;

    public EventInfo(Long id) {
        this.id = id;
    }

    public int incrementCountAccessByName(){
        return ++countAccessByName;
    }

    public int incrementCountPricesWereQueried(){
        return ++countPricesWereQueried;
    }

    public int incrementCountTicketsWereBooked(){
        return ++countTicketsWereBooked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCountAccessByName() {
        return countAccessByName;
    }

    public void setCountAccessByName(int countAccessByName) {
        this.countAccessByName = countAccessByName;
    }

    public int getCountPricesWereQueried() {
        return countPricesWereQueried;
    }

    public void setCountPricesWereQueried(int countPricesWereQueried) {
        this.countPricesWereQueried = countPricesWereQueried;
    }

    public int getCountTicketsWereBooked() {
        return countTicketsWereBooked;
    }

    public void setCountTicketsWereBooked(int countTicketsWereBooked) {
        this.countTicketsWereBooked = countTicketsWereBooked;
    }
}
