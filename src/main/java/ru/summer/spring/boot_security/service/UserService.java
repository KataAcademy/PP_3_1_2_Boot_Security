package ru.summer.spring.boot_security.service;

import ru.summer.spring.boot_security.model.User;

import java.util.List;

public interface UserService {

    void add(User user);

    void update(User user);

    void delete(Long id);

    List<User> getAllUsers();

}
