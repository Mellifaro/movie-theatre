package ua.epam.spring.hometask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.user.UserService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
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

    @Autowired
    private ObjectMapper objectMapper;

    public void saveUsers(){

    }

    @GetMapping(value = "/")
    public String getAllUsers(ModelMap modelMap){
        List<User> users = (List<User>)userService.getAll();
        modelMap.put("users", users);
        return "index";
    }

    @PostMapping(value = "/uploadFile")
    public String uploadMultipleFileHandler(@RequestParam("file") MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            List<User> userList = objectMapper.readValue(file.getBytes(), typeFactory.constructCollectionType(List.class, User.class));
            userList.forEach(userService::save);
            System.out.println(userList);
            return "redirect:uploadSuccess";
        }
        return "redirect:uploadFailure";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
