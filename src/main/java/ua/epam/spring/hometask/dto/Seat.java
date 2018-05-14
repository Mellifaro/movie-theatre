package ua.epam.spring.hometask.dto;

public class Seat {
    private long seat;
    private SeatStatus currentStatus;

    public Seat() {
    }

    public Seat(long seat) {
        this.seat = seat;
        this.currentStatus = SeatStatus.SIMPLE;
    }

    public Seat(long seat, SeatStatus currentStatus) {
        this.seat = seat;
        this.currentStatus = currentStatus;
    }

    public long getSeat() {
        return seat;
    }

    public void setSeat(long seat) {
        this.seat = seat;
    }

    public SeatStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(SeatStatus currentStatus) {
        this.currentStatus = currentStatus;
    }
}
