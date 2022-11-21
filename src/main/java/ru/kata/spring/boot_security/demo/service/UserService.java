package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();

    public User getUser(int id);

    public void save(User user);

    public void updateUser(int id, User user);

    public void deleteUser(int id);
}
