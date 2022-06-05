package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User add(User user);

    List<User> listUsers();

    User get(int id);

    void update(int id, User updatedUser);

    void delete(int id);

    User getByNickName(String name);
}
