package ru.kata.spring.boot_security.demo.services;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> allUsers();


    User getUserById(Long id);

    User findUserByUserName(String username);


    void saveUser(User user);
    void saveUser(User user, Set<Role> roleSet);


    void updateUserDetails(User user);

    void deleteUser(Long id);
}
