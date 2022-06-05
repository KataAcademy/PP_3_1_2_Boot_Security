package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userDao) {
        this.userRepository = userDao;
    }

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> listUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User get(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User getByNickName(String name) {
        return userRepository.findByNickName(name);
    }

    @Override
    public void update(int id, User updatedUser) {
        User user = userRepository.findById(id).get();
        user.update(updatedUser);
        userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByNickName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getNickName(), user.getPassword(), user.getAuthorities());
    }
}
