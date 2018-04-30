package ua.epam.spring.hometask.service.payment;

import org.springframework.lang.NonNull;
import ua.epam.spring.hometask.domain.User;

public interface PaymentService {

    User putMoney(@NonNull User user, Float amount);

    User withdrawMoney(@NonNull User user, Float amount);
}
