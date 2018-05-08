package ua.epam.spring.hometask.soap.endpoints;

import org.springframework.security.crypto.password.PasswordEncoder;
import ua.epam.spring.hometask.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ua.epam.spring.hometask.service.user.UserService;
import ua.epam.spring.hometask.soap.messages.*;

import java.util.ArrayList;


@Endpoint
public class UserEndpoint {
    private static final String NAMESPACE_URI = "http://epam.com/soap";

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserEndpoint(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAllUsers(@RequestPayload GetAllUsersRequest request){
        GetAllUsersResponse response = new GetAllUsersResponse();
        response.setUser(new ArrayList(userService.getAll()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserByIdRequest")
    @ResponsePayload
    public GetUserByIdResponse getUserById(@RequestPayload GetUserByIdRequest request){
        GetUserByIdResponse response = new GetUserByIdResponse();
        response.setUser(userService.getById(request.getId()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addUserRequest")
    @ResponsePayload
    public AddUserResponse addUser(@RequestPayload AddUserRequest request){
        AddUserResponse response = new AddUserResponse();
        User user = request.getUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(request.getUser());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request){
        DeleteUserResponse response = new DeleteUserResponse();
        userService.remove(userService.getById(request.getId()));
        return response;
    }
}
