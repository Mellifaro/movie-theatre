package ua.epam.spring.hometask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.user.UserService;

import java.util.Collections;
import java.util.List;

/**
 * Created by Viktor_Skapoushchenk on 4/23/2018.
 */
@Controller
@RequestMapping(value = "/users")
public class UserController{

    @Autowired
    private UserService userService;

    public void saveUsers(){

    }

    @GetMapping(value = "/")
    public String getAllUsers(ModelMap modelMap){
        List<User> users = (List<User>)userService.getAll();
        modelMap.put("users", users);
        return "index";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
