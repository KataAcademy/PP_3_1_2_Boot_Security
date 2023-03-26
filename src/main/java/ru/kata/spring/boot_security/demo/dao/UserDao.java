package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;
import java.util.Optional;
import java.util.Set;

public interface UserDao {
    Optional<User> findUserByEmailFetchRoles(String username);
    Set<User> findAllFetchRoles();
    User save(User user);
    void deleteById(Long id);
}
