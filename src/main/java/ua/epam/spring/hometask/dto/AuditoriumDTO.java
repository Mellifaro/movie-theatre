package ua.epam.spring.hometask.dto;

import java.util.List;

public class AuditoriumDTO {
    private String name;
    private List<List<Seat>> seatMatrix;

    public AuditoriumDTO() {
    }

    public AuditoriumDTO(String name, List<List<Seat>> seatMatrix) {
        this.name = name;
        this.seatMatrix = seatMatrix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<List<Seat>> getSeatMatrix() {
        return seatMatrix;
    }

    public void setSeatMatrix(List<List<Seat>> seatMatrix) {
        this.seatMatrix = seatMatrix;
    }
}
