package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.User;

import java.util.Optional;

public interface UserDAO extends BaseDAO<User> {

    Optional<User> getUserByEmail(String email);
}
