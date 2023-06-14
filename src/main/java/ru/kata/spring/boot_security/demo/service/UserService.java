package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;

    //Для создания UserDetails используется интерфейс UserDetailsService, с единственным методом:
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s not found", username));
        }
        return user;
    }
//    public User(String username, String password, String email, Collection<Role> roles) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.roles = roles;
//    }

    public void init() {
        roleService.saveRole(new Role("ROLE_ADMIN"));
        roleService.saveRole(new Role("ROLE_USER"));
        userRepository.save(new User("userAdmin", "$2a$12$8ba3pDR6kEhzdQd8yScQaetFUdsJtJx9hFmEtOcpOV7PNwaJRMLu2","user1Admin@gmail.com",
                roleService.getRoleById(2L), roleService.getRoleById(1L)));
        userRepository.save(new User("user", "$2a$12$8ba3pDR6kEhzdQd8yScQaetFUdsJtJx9hFmEtOcpOV7PNwaJRMLu2","user1@gmail.com",
                roleService.getRoleById(2L)));
        userRepository.save(new User("admin","$2a$12$8ba3pDR6kEhzdQd8yScQaetFUdsJtJx9hFmEtOcpOV7PNwaJRMLu2","admin1@gmail.com",
                roleService.getRoleById(1L)));
    }


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
        userRepository.deleteUserById(userId);
    }



}
