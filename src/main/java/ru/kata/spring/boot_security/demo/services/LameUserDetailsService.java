package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

@Service
public class LameUserDetailsService implements UserDetailsService {

    private final UserRepository userRep;

    @Autowired
    public LameUserDetailsService(UserRepository userRep) {
        this.userRep = userRep;
    }

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRep.findUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Unknown user: " + username);
        }

        return user;
    }
}
