package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.UserDiscountInfo;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * @author Viktor Skapoushchenko
 */
public interface UserDiscountInfoDAO {

    Optional<UserDiscountInfo> getByUserId(@Nonnull Long userId);

    UserDiscountInfo save(@Nonnull UserDiscountInfo userDiscountInfo);

    void remove (@Nonnull Long userId, @Nonnull String discountType);
}
