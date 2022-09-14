package ru.kata.spring.boot_security.demo.dao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);
    List<User> getAllUsers();
    void deleteUser(Long id);
    User getUserById(Long id);
    void editUser(User user);
    User getUserByUsername(String username);
}
