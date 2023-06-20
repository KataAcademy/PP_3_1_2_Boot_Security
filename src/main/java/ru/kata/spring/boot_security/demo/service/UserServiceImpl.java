package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;

    public void saveUser(User user) {
        System.out.println("bla");
        if (!user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        System.out.println("blabla");
        userRepository.save(user);
        System.out.println("blablabla");
    }


    public void init() {
        roleService.saveRole(new Role("ROLE_ADMIN"));
        roleService.saveRole(new Role("ROLE_USER"));
        saveUser(new User("userAdmin", "100", "user1Admin@gmail.com", roleService.getRoleById(2L), roleService.getRoleById(1L)));
        saveUser(new User("user", "100", "user1@gmail.com", roleService.getRoleById(2L)));
        saveUser(new User("admin", "100", "admin1@gmail.com", roleService.getRoleById(1L)));
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    @Transactional(readOnly = true)
    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        if (userFromDb.isEmpty()) {
            throw new NullPointerException();
        }
        return userFromDb.get();
    }

    @Transactional(readOnly = true)
    public List<User> allUsers() {
        return userRepository.findUsers();
    }


    public void deleteUser(Long userId) {
        userRepository.deleteUserById(userId);
    }


}
