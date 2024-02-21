package org.marx.spring.boot_security.service;

import org.marx.spring.boot_security.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findUserById(Long userId);
    List<User> getUserList();
    void create(User user);
    void deleteById (Long userId);
    void update(User updateUser);
    Optional<User> findByUsername(String username);
}
