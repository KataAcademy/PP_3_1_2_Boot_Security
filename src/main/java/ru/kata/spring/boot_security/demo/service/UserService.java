package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void addUser(User user);
    List<User> getAllUsers();
    void deleteUser(Long id);
    User getUserById(Long id);
    void editUser(User user);
}
