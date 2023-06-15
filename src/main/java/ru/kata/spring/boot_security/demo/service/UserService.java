package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);


    void init();


    User findByUsername(String username);


    User findUserById(Long userId);


    List<User> allUsers();


    public void deleteUser(Long userId);


}
