package ua.epam.spring.hometask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.epam.spring.hometask.service.user.UserService;

/**
 * Created by Viktor_Skapoushchenk on 4/23/2018.
 */
@Controller
@RequestMapping(value = "/users")
public class UserController{

    private UserService userService;

    public void saveUsers(){

    }

    @GetMapping(value = "/")
    public String getAllUsers(){
        return "index";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
