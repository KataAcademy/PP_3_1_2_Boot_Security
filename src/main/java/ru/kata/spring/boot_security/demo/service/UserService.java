package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;


    //Для создания UserDetails используется интерфейс UserDetailsService, с единственным методом:
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s not found", username));
        }
        return user;
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//                mapRolesToAuthorities(user.getRoles()));
    }

//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
//        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
//    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }
    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        if (userFromDb.isEmpty()) {
            throw new NullPointerException();
        }
        return userFromDb.get();
    }

    public List<User> allUsers() {
        return userRepository.findUsers();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
        }
    }



}
