package ua.epam.spring.hometask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.dto.AmountDTO;
import ua.epam.spring.hometask.security.AuthorizedUser;
import ua.epam.spring.hometask.service.payment.PaymentService;
import ua.epam.spring.hometask.service.user.UserService;

@Controller
@RequestMapping(value = "/account")
public class PaymentController {
    private static final String MY_ACCOUNT_VIEW = "myAccount";

    private PaymentService paymentService;
    private UserService userService;

    @Autowired
    public PaymentController(PaymentService paymentService, UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @GetMapping
    public String getAccountInfo(){
        return MY_ACCOUNT_VIEW;
    }

    @PostMapping(value = "/money/put", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String putMoney(@RequestBody AmountDTO amountDTO){
        AuthorizedUser userDetails = (AuthorizedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByEmail(amountDTO.getEmail());
        user = paymentService.putMoney(user, amountDTO.getAmount());
        userDetails.setBalance(user.getBalance());
        return MY_ACCOUNT_VIEW;
    }

    @PostMapping(value = "/money/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String withdrawMoney(@RequestBody AmountDTO amountDTO){
        AuthorizedUser userDetails = (AuthorizedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByEmail(amountDTO.getEmail());
        user = paymentService.withdrawMoney(user, amountDTO.getAmount());
        userDetails.setBalance(user.getBalance());
        return MY_ACCOUNT_VIEW;
    }
}
