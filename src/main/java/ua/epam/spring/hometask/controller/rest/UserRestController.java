package ua.epam.spring.hometask.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.user.UserService;

import java.util.Collection;

@RestController
@RequestMapping(value = "/rest/users")
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<User> getAllUsers(){
        return userService.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable("id") Long id){
        return userService.getById(id);
    }
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveUser(@RequestBody User user){
        userService.save(user);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        userService.remove(userService.getById(id));
    }
}
