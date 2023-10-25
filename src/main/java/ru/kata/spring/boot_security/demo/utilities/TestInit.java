package ru.kata.spring.boot_security.demo.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleServiceImp;
import ru.kata.spring.boot_security.demo.services.UserServiceImp;

import javax.annotation.PostConstruct;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

@Component
public class TestInit {

    private final UserServiceImp userService;
    private final RoleServiceImp roleServiceImp;

    @Autowired
    public TestInit(UserServiceImp userService, RoleServiceImp roleServiceImp) {
        this.userService = userService;
        this.roleServiceImp = roleServiceImp;
    }


    @PostConstruct
    public void initDataBase() {

        roleServiceImp.saveRole(new Role("ADMIN"));
        roleServiceImp.saveRole(new Role("USER"));
        User user = new User();
        User admin = new User();
        User uAdmin = new User();
        Role role = roleServiceImp.getRoleByName("USER");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setUserName("user");
        user.setEmail("user@user.com");
        user.setPassword("user");
        user.setRoles(userRoles);
        userService.saveUser(user); //почему не работает?
    }


}
