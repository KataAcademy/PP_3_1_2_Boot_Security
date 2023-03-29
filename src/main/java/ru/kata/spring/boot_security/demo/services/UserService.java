package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;

public interface UserService {
    List<User> findAll();
    User saveOrUpdate(User user);
    void deleteById(Long id);
}
