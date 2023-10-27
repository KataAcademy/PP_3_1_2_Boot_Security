package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository  userRep;
    private final PasswordEncoder passwordEncoder;
    private final RoleServiceImp roleService;

    @Autowired
    public UserServiceImp(UserRepository userRep,
                          PasswordEncoder passwordEncoder, RoleServiceImp roleService) {
        this.userRep = userRep;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
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

    @Override
    public User findUserByUserName(String userName) {
        return userRep.findByUserName(userName);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRep.save(user);
    }

    @Override
    @Transactional
    public void saveUser(User user, Set<Role> roleSet) {
        Set<Role> roles = new HashSet<>();
        for (Role role : roleSet) {
            roles.add(roleService.getRoleByName(role.getRoleName()));
        }
        user.setRoles(roles);
        saveUser(user);
    }

    @Override
    @Transactional
    public void updateUserDetails(User user) {
        User userFromDb = getUserById(user.getId());
        userFromDb.setUserName(user.getUsername());
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
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
