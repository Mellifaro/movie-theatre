package ua.epam.spring.hometask.service.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.exceptions.NotEnoughMoneyException;
import ua.epam.spring.hometask.service.user.UserService;

import java.util.Objects;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public User putMoney(@NonNull User user, Double amount) {
        checkAmountAndUser(user, amount);
        double currentBalance = user.getBalance();
        user.setBalance(currentBalance + amount);
        return userService.save(user);
    }

    @Override
    @Transactional
    public User withdrawMoney(@NonNull User user, Double amount) {
        checkAmountAndUser(user, amount);
        double currentBalance = user.getBalance();
        if(amount > currentBalance){
            throw new NotEnoughMoneyException("Amount for withdraw is bigger than current balance");
        }
        user.setBalance(currentBalance - amount);
        return userService.save(user);
    }

    private void checkAmountAndUser(@NonNull User user, Double amount){
        Objects.requireNonNull(user, "User must not be null");
        Objects.requireNonNull(user, "Amount of money must not be null");
        if(amount < 0){
            throw new IllegalArgumentException("Amount must not be less than 0.00");
        }
    }
}
