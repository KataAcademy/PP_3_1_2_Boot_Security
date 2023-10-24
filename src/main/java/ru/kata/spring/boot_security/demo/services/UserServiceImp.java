package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository  userRep;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRep,
                          PasswordEncoder passwordEncoder) {
        this.userRep = userRep;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> allUsers() {
        return userRep.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRep.getById(id);
    }

    /*@Override
    public User findUserByUserName(String userName) {
        return userRep.findByUserName(userName);
    }*/

    @Override
    @Transactional
    public void saveUser(User user) {
        userRep.save(user);
    }

    @Override
    @Transactional
    public void updateUserDetails(User user) {
        User userFromDb = getUserById(user.getId());
        userFromDb.setUserName(user.getUsername());
        userFromDb.setEmail(user.getEmail());
        userFromDb.setRoles(user.getRoles());
        userFromDb.setPassword(passwordEncoder.encode(user.getPassword()));
        userRep.save(userFromDb);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRep.deleteById(id);
    }
}
