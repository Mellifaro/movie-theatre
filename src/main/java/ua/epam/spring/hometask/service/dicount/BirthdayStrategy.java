package ua.epam.spring.hometask.service.dicount;

import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.DiscountType;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Set;

@Component
public class BirthdayStrategy implements DiscountStrategy {
    private static final byte DISCOUNT = 5;
    private static final DiscountType DISCOUNT_TYPE = DiscountType.BIRTHDAY;

    @Override
    public int getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull Set<Ticket> tickets) {
        if(user != null && user.getBirthday() != null){
            LocalDate now = LocalDate.now();
            LocalDate birthday = user.getBirthday();

            LocalDate start = birthday.minusDays(5).withYear(now.getYear());
            LocalDate end = birthday.plusDays(5).withYear(now.getYear());
            if(now.isAfter(start) && now.isBefore(end)){
                return DISCOUNT;
            }
        }
        return 0;
    }

    @Override
    public Set<Ticket> apply(@Nullable User user, @Nonnull Event event, @Nonnull Set<Ticket> tickets) {
        if(user == null){
            return tickets;
        }
        tickets.forEach(ticket -> {
            ticket.setDiscount(DISCOUNT);
            double price = ticket.getPrice() * (100 - DISCOUNT) / 100;
            ticket.setPrice(price);
            ticket.setDiscountType(DISCOUNT_TYPE);
        });
        return tickets;
    }

    public DiscountType getDiscountType() {
        return DISCOUNT_TYPE;
    }
}
