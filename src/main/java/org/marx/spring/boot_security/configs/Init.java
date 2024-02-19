package org.marx.spring.boot_security.configs;

import org.marx.spring.boot_security.model.Role;
import org.marx.spring.boot_security.model.User;
import org.marx.spring.boot_security.service.RoleService;
import org.marx.spring.boot_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void initUsers() {

        Role adminRole = new Role("ROLE_ADMIN");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);

        roleService.save(adminRole);

        Role userRole = new Role("ROLE_USER");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);

        roleService.save(userRole);

        User admin = new User("ADMIN", 20, "ADMIN", adminRoles);

        userService.create(admin);

        User user = new User("user", 20, "user", userRoles);

        userService.create(user);
    }
}
