package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.TotalDiscountInfo;

public interface TotalDiscountInfoDAO {

    TotalDiscountInfo getByDiscountName(String discountName);

    TotalDiscountInfo save(TotalDiscountInfo discountInfo);

    void remove(String discountName);
}
