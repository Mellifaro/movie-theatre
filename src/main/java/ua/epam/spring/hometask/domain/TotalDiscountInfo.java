package ua.epam.spring.hometask.domain;

import java.util.Objects;

public class TotalDiscountInfo {
    private DiscountType discountType;
    private Integer amount;

    public TotalDiscountInfo() {
    }

    public TotalDiscountInfo(DiscountType discountType, Integer amount) {
        this.discountType = discountType;
        this.amount = amount;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalDiscountInfo that = (TotalDiscountInfo) o;
        return discountType == that.discountType &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountType, amount);
    }
}
