package ua.epam.spring.hometask.dto;

public class AmountDTO {
    private String email;
    private double amount;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
