package org.marx.spring.boot_security.service;

import org.marx.spring.boot_security.model.Role;
import org.marx.spring.boot_security.model.User;
import org.marx.spring.boot_security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
    public void create(User user) {
        userRepository.findByUsername(user.getUsername());
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public void update(User updateUser) {
        if (updateUser != null) {
            userRepository.save(updateUser);
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}