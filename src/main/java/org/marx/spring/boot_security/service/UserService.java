package org.marx.spring.boot_security.service;

import org.marx.spring.boot_security.model.User;

import java.util.List;

public interface UserService {
    User findUserById(Long userId);
    List<User> getUserList();
    void create(User user);
    void deleteById (Long userId);
    void update(User updateUser);
    User findByUsername(String username);
}
