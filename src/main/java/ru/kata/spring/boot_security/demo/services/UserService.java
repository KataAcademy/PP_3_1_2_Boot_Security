package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByName(String name);
    Optional<User> findByNameFetch(String name);
    User saveOrUpdate(User user, String role);
    void deleteById(Long id);
}
