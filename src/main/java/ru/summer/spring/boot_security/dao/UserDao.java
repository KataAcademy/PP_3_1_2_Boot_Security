package ru.summer.spring.boot_security.dao;

import ru.summer.spring.boot_security.model.User;

import java.util.List;

/**
 * Интерфейс для взаимодействия с данными пользователя.
 * Определяет основные операции CRUD (create, read, update, delete).
 */
public interface UserDao {

    void add(User user);

    void update(User user);

    void delete(Long id);

    List<User> getAllUsers();

}

