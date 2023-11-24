package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRep;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRep, PasswordEncoder passwordEncoder) {
        this.userRep = userRep;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
        return userRep.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRolename())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<User> allUsers() {
        return userRep.findAll();
    }

    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRep.save(user);
    }

    @Transactional(readOnly = true)
    public User showUserDetails(Long id) {
        Optional<User> user = userRep.findById(id);
        return user.orElse(null);
    }

    @Transactional
    public void updateUserDetails(Long id, User user) {
        User userFromDb = showUserDetails(id);
        Optional.ofNullable(user.getUsername()).ifPresent(userFromDb::setUsername);
        /*
        // todo поменять логику смены пароля. Сейчас эта опция отключена
            Optional.ofNullable(user.getPassword())
                .ifPresent(password -> userFromDb.
                        setPassword(passwordEncoder
                                .encode(password)));*/
        Optional.ofNullable(user.getEmail()).ifPresent(userFromDb::setEmail);
        Optional.ofNullable(user.getFirstName()).ifPresent(userFromDb::setFirstName);
        Optional.ofNullable(user.getLastName()).ifPresent(userFromDb::setLastName);
        Optional.ofNullable(user.getRoles()).ifPresent(userFromDb::setRoles);
        userRep.save(userFromDb);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRep.deleteById(id);
    }
}
