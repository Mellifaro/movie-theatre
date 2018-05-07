package ua.epam.spring.hometask.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.dto.UserDTO;

@Component
public class UserUtils {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserUtils(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserFromDTO(UserDTO userDTO){
        User user = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getBirthday());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return user;
    }
}
