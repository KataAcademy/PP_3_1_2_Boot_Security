package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findUserByEmailFetchRoles(String username);
    List<User> findAllFetchRoles();
    User save(User user);
    void deleteById(Long id);

    Optional<User> findByIdFetchRoles(Long id);
}
