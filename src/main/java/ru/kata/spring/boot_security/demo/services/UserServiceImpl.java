package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAllFetchRoles().stream().distinct().toList();
    }


    @Override
    @Transactional
    public User saveOrUpdate(User user) {
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(roleDao.findByName(user.getRoles().stream().findFirst().orElseThrow().getName()).orElseThrow()));
            return userDao.save(user);
        }
        User foundUser = userDao.findByIdFetchRoles(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Can't find user to update"));
        resolvePassword(user, foundUser.getPassword());
        resolveRoles(user, user.getRoles(), foundUser.getRoles());
        userDao.save(user);
        return user;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    private void resolvePassword(User user, String encodedPassword) {
        if (user.getPassword().equals("") || user.getPassword().isEmpty()) {
            user.setPassword(encodedPassword);
            return;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    private void resolveRoles(User user, Set<Role> roles, Set<Role> foundRoles) {
        if (roles == null || roles.isEmpty()) {
            user.setRoles(foundRoles);
            return;
        }
        Role newRole = roleDao.findByName(roles.stream().findAny().orElseThrow().getName()).orElseThrow();
        if (foundRoles.contains(newRole)) {
            user.setRoles(foundRoles);
            return;
        }
        user.setRoles(foundRoles);
        user.addRole(newRole);
    }
}
