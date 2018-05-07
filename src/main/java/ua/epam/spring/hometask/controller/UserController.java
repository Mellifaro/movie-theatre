package ua.epam.spring.hometask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.epam.spring.hometask.domain.Role;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.dto.UserDTO;
import ua.epam.spring.hometask.service.user.UserService;
import ua.epam.spring.hometask.util.UserUtils;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by Viktor_Skapoushchenk on 4/23/2018.
 */
@Controller
@RequestMapping(value = "/users")
public class UserController{

    private UserService userService;
    private UserUtils userUtils;
    private ObjectMapper objectMapper;

    public UserController(UserService userService, UserUtils userUtils, ObjectMapper objectMapper) {
        this.userService = userService;
        this.userUtils = userUtils;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public String getAllUsers(ModelMap modelMap){
        List<User> users = (List<User>)userService.getAll();
        modelMap.put("users", users);
        return "users";
    }

    @PostMapping(value = "/register")
    public void register(@RequestBody UserDTO userDTO){
        User user = userUtils.getUserFromDTO(userDTO);
        userService.save(user);
    }

    @PostMapping(value = "/uploadFile")
    public String uploadMultipleFileHandler(@RequestParam("file") MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            List<User> userList = objectMapper.readValue(file.getBytes(), typeFactory.constructCollectionType(List.class, User.class));
            userList.forEach(user -> {
                user.setRoles(EnumSet.of(Role.ROLE_USER));
                userService.save(user);
            });
            return "redirect:/users";
        }
        return "redirect:/error";
    }
}
