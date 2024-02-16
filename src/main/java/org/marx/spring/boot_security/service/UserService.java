package org.marx.spring.boot_security.service;

import org.marx.spring.boot_security.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User findUserById(Long userId);
    List<User> getUserList();
    boolean createUser(User user);
    boolean deleteUser(Long userId);
    User updateUser(User updateUser);
    User findByUsername(String username);
}