package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> allUsers();
    User findUser(Long id);

    User findUser(String userName);

    void saveUser(User user);
    void deleteUser(Long id);
}
