package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.dao.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Optional;
import java.util.Set;

@Component
public class UserResolverImpl implements UserResolver {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResolverImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User resolveUserPasswordAndRole(User user, String role) {
        if (user.getId() == null) {
            Optional<Role> roleByName = roleRepository.findRoleByName("ROLE_".concat(role));
            user.addRole(roleByName.orElseThrow());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return user;
        }
        User foundUser = userRepository.findById(user.getId()).orElseThrow(() -> new UsernameNotFoundException("User doesnt exist"));
        resolvePassword(user, foundUser.getPassword());
        resolveRole(user, role, foundUser.getRoles());
        return user;
    }

    private void resolveRole(User user, String role, Set<Role> foundRoles) {
        if (role == null) {
            user.setRoles(foundRoles);
            return;
        }
        Optional<Role> newRole = roleRepository.findRoleByName("ROLE_".concat(role));
        if (foundRoles.contains(newRole.orElseThrow())) {
            user.setRoles(foundRoles);
            return;
        }
        user.setRoles(foundRoles);
        user.addRole(newRole.orElseThrow());
    }

    private void resolvePassword(User user, String encodedPassword) {
        if (user.getPassword().equals("")) {
            user.setPassword(encodedPassword);
            return;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
