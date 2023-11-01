package ru.kata.spring.boot_security.demo.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class TestInit {


    private final UserService    userService;
    private final RoleRepository roleRep;

    @Autowired
    public TestInit(UserService userService, RoleRepository roleRep) {
        this.userService = userService;
        this.roleRep = roleRep;
    }


    @PostConstruct
    public void initDataBase() {

        roleRep.save(new Role("ROLE_ADMIN"));
        roleRep.save(new Role("ROLE_USER"));
        Set<Role> userRoles = new HashSet<>();
        Set<Role> adminRoles = new HashSet<>();
        Set<Role> uAdminRoles = new HashSet<>();
        userRoles.add(roleRep.findRoleByRolename("ROLE_USER"));
        adminRoles.add(roleRep.findRoleByRolename("ROLE_ADMIN"));
        uAdminRoles.addAll(userRoles);
        uAdminRoles.addAll(adminRoles);
        User user1 = new User(
                "user",
                "Ivan",
                "Ivanov",
                "user@user.com",
                "user",
                userRoles
        );
        User user2 = new User(
                "admin",
                "Noah",
                "Bernstein",
                "admin@admin.com",
                "admin",
                adminRoles);
        User user3 = new User(
                "usmin",
                "Sarah",
                "Kahabidze",
                "user@admin.com",
                "usmin",
                uAdminRoles
        );


        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
    }


}
