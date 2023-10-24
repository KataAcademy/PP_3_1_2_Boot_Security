package ru.kata.spring.boot_security.demo.services;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> allUsers();


    User getUserById(Long id);

    /*User findUserByUserName(String userName);*/


    void saveUser(User user);


    void updateUserDetails(User user);

    void deleteUser(Long id);
}
