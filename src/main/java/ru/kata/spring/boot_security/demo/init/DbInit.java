package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class DbInit {

    private final UserService userService;

    @Autowired
    public DbInit(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void initDbUsers() {

        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        User admin = new User();
        Set<Role> adminRoles = new HashSet<>();
        Collections.addAll(adminRoles, roleAdmin, roleUser);
        admin.setId(1L);
        admin.setUsername("admin");
        admin.setPassword("$2a$12$hWxbaYoFF0vBCa8m.VVl8up5oOpvb31uC.G3szPEBIMxHH0lsVuCS");
        admin.setSurname("admin");
        admin.setAge(21);
        admin.setEmail("admin@mail.ru");
        admin.setRoles(adminRoles);
        userService.saveUser(admin);

        User user = new User();
        Set<Role> userRoles = new HashSet<>();
        Collections.addAll(userRoles, roleUser);
        user.setId(2L);
        user.setUsername("user");
        user.setPassword("$2a$12$.dksYsGuStqzMfofRpdYOudElB3pT/KqqAxWvCyTfWZHhh2QcKPM2");
        user.setSurname("user");
        user.setAge(22);
        user.setEmail("user@mail.ru");
        user.setRoles(userRoles);
        userService.saveUser(user);

    }
}
