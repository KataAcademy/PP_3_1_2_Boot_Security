package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userBuId = userRepository.findById(id);
        if (userBuId.isPresent()) {
            return userBuId.get();
        } else {
            throw new UsernameNotFoundException(String.format("User id - '%s' not found: ", id));
        }
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }


    @Override
    public void saveUser(User user) {
//        user.add(new Role("ROLE_USER"));
        user.setPassword(user.getPassword());
        userRepository.saveAndFlush(user);
    }

    @Override
    public void updateUser(Long id, User user) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isPresent()) {
            User userRepos = userById.get();
            userRepos.setId(id);
            userRepos.setUsername(user.getUsername());
            userRepos.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));
            System.out.println(user.getPassword());
            userRepos.setSurname(user.getSurname());
            userRepos.setEmail(user.getEmail());
            userRepos.setAge(user.getAge());
            userRepos.setRole(user.getRoles());
            userRepository.save(userRepos);
        } else {
            throw new UsernameNotFoundException(String.format("User '%s' not found: ", user));
        }
    }


    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}
