package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService extends UserDetailsService {
    public List<User> getAllUsers();

    public User getUserById(Long id);

    public User getUserByUsername(String username);

    public void saveUser(User user);

    public void updateUser(Long id, User user);

    public void deleteUserById(Long id);

    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles);
}
