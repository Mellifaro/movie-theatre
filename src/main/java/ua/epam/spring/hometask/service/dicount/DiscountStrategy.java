package ua.epam.spring.hometask.service.dicount;

import ua.epam.spring.hometask.domain.DiscountType;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public interface DiscountStrategy {

    int getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull Set<Ticket> tickets);

    Set<Ticket> apply(@Nullable User user, @Nonnull Event event, @Nonnull Set<Ticket> tickets);

    DiscountType getDiscountType();
}
