package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.TotalDiscountInfo;

import java.util.Optional;

/**
 * @author Viktor Skapoushchenko
 */
public interface TotalDiscountInfoDAO {

    Optional<TotalDiscountInfo> getByDiscountName(String discountName);

    TotalDiscountInfo save(TotalDiscountInfo discountInfo);

    void remove(String discountName);
}
