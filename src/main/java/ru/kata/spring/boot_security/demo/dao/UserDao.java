package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    List<User> allUsers();
    User findUser(Long id);

    void saveUserDetails(User user);
    void deleteUser(Long id);
}
