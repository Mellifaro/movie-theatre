package ua.epam.spring.hometask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.epam.spring.hometask.service.user.UserService;

/**
 * Created by Viktor_Skapoushchenk on 4/23/2018.
 */
@Controller
public class UserController{

    private UserService userService;

    public void saveUsers(){

    }

    public void getAllUsers(){

    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
