package ru.kata.spring.boot_security.demo.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleServiceImp;
import ru.kata.spring.boot_security.demo.services.UserServiceImp;

import javax.annotation.PostConstruct;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
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
        Set<Role> userRoles = new HashSet<>();
        Set<Role> adminRoles = new HashSet<>();
        Set<Role> uAdminRoles = new HashSet<>();
        userRoles.add(roleServiceImp.getRoleByName("USER"));
        adminRoles.add(roleServiceImp.getRoleByName("ADMIN"));
        uAdminRoles.addAll(userRoles);
        uAdminRoles.addAll(adminRoles);
        user.setUserName("user");
        user.setEmail("user@user.com");
        user.setPassword("user");
        admin.setUserName("admin");
        admin.setEmail("admin@admin.com");
        admin.setPassword("admin");
        uAdmin.setUserName("uadmin");
        uAdmin.setEmail("user@admin.com");
        uAdmin.setPassword("uadmin");

        userService.saveUser(user, userRoles);
        userService.saveUser(admin, adminRoles);
        userService.saveUser(uAdmin, uAdminRoles);
    }


}
