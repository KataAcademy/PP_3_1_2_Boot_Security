package ru.kata.spring.boot_security.until;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.models.Person;
import ru.kata.spring.boot_security.models.Role;
import ru.kata.spring.boot_security.services.AdminService;
import ru.kata.spring.boot_security.services.RegistrationService;
import ru.kata.spring.boot_security.services.RoleService;

import javax.annotation.PostConstruct;

@Component
public class MyDataInitializer {

    private final AdminService adminService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationService registrationService;

    @Autowired
    public MyDataInitializer(AdminService adminService, RoleService roleService, PasswordEncoder passwordEncoder, RegistrationService registrationService) {
        this.adminService = adminService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.registrationService = registrationService;
    }

    @PostConstruct
    public void initializeData() {
        if (adminService.getAllUsers().isEmpty()) {
            Role adminRole = new Role("ROLE_ADMIN");
            roleService.saveRole(adminRole);
            Person adminUser = new Person();
            adminUser.setFirstName("admin");
            adminUser.setLastName("admin");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            registrationService.registerAdmin(adminUser);
            System.out.println("Users created:");
            System.out.println("Admin: username=admin, password=admin");
        }
    }
}
