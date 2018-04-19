package ua.epam.spring.hometask.dao;

import com.sun.org.apache.xpath.internal.operations.String;
import ua.epam.spring.hometask.domain.UserDiscountInfo;

import javax.annotation.Nonnull;

public interface UserDiscountInfoDAO {

    UserDiscountInfo getByUserId(@Nonnull Long userId);

    UserDiscountInfo save(@Nonnull UserDiscountInfo userDiscountInfo);

    void remove (@Nonnull Long userId, @Nonnull String discountType);
}
