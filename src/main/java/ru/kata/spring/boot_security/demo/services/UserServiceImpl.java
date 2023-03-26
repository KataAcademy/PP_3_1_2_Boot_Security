package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.dao.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.dao.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.security.UserResolver;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDao userDao;
    private final UserResolver userResolver;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserDao userDao, UserResolver userResolver) {
        this.userRepository = userRepository;
        this.userDao = userDao;
        this.userResolver = userResolver;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAllFetchRoles().stream().toList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findUserByEmail(name);
    }

    @Override
    public Optional<User> findByNameFetch(String name) {
        return userDao.findUserByEmailFetchRoles(name);
    }

    @Override
    @Transactional
    public User saveOrUpdate(User user, String role) {
        User resolvedUser = userResolver.resolveUserPasswordAndRole(user, role);
        userDao.save(resolvedUser);
        return user;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }
}
