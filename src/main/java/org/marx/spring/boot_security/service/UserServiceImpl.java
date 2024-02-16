package org.marx.spring.boot_security.service;

import org.marx.spring.boot_security.model.Role;
import org.marx.spring.boot_security.model.User;
import org.marx.spring.boot_security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }
    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
    @Override
    @Transactional
    public boolean createUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(user.getPassword());
        userRepository.save(user);
        return true;
    }
    @Override
    @Transactional
    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
    @Override
    @Transactional
    public User updateUser(User updateUser){
        return userRepository.save(updateUser);
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}