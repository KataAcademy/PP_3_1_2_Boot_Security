package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {

    void saveUser(User user);

    void removeUserById(long id);

    User getUserById(long id);

    void updateUser(User user);

    List<User> getAllUsers();

    User getUserByUsername(String username);

}