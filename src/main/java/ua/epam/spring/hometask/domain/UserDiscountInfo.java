package ua.epam.spring.hometask.domain;

import java.util.Map;
import java.util.Objects;

public class UserDiscountInfo {
    private Long userId;
    private Map<DiscountType, Integer> discountMap;

    public UserDiscountInfo() {
    }

    public UserDiscountInfo(Long userId, Map<DiscountType, Integer> discountMap) {
        this.userId = userId;
        this.discountMap = discountMap;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Map<DiscountType, Integer> getDiscountMap() {
        return discountMap;
    }

    public void setDiscountMap(Map<DiscountType, Integer> discountMap) {
        this.discountMap = discountMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDiscountInfo that = (UserDiscountInfo) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "UserDiscountInfo{" +
                "userId=" + userId +
                ", discountMap=" + discountMap +
                '}';
    }
}
