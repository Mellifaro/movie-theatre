package ua.epam.spring.hometask.service.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.exceptions.NotEnoughMoneyException;
import ua.epam.spring.hometask.service.user.UserService;

import java.util.Objects;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private UserService userService;

    @Override
    public User putMoney(@NonNull User user, Float amount) {
        checkAmountAndUser(user, amount);
        float currentBalance = user.getBalance();
        user.setBalance(currentBalance + amount);
        return userService.save(user);
    }

    @Override
    public User withdrawMoney(@NonNull User user, Float amount) {
        checkAmountAndUser(user, amount);
        float currentBalance = user.getBalance();
        if(amount > currentBalance){
            throw new NotEnoughMoneyException("Amount for withdraw is bigger than current balance");
        }
        user.setBalance(currentBalance - amount);
        return userService.save(user);
    }

    private void checkAmountAndUser(@NonNull User user, Float amount){
        Objects.requireNonNull(user, "User must not be null");
        Objects.requireNonNull(user, "Amount of money must not be null");
        if(amount < 0){
            throw new IllegalArgumentException("Amount must not be less than 0.00");
        }
    }
}
