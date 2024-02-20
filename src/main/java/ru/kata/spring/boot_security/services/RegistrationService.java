package ru.kata.spring.boot_security.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.models.Person;
import ru.kata.spring.boot_security.models.Role;
import ru.kata.spring.boot_security.repositories.PeopleRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public RegistrationService(@Lazy PeopleRepository peopleRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public void registerAdmin(Person person) {
        String[] roles = new String[]{"ROLE_ADMIN"};
        setUserRoles(person, roles);
        peopleRepository.save(person);
    }

    public void registerUser(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        String[] roles = new String[]{"ROLE_USER"};
        setUserRoles(person, roles);

        peopleRepository.save(person);
    }

    private void setUserRoles(Person person, String[] rolesNames) {
        Set<Role> rolesSet = new HashSet<>();
        for (String roleName : rolesNames) {
            Role role = roleService.findByName(roleName);
            if (role == null) {
                role = new Role(roleName);
                roleService.saveRole(role);
            }
            rolesSet.add(role);
        }
        person.setRoles(rolesSet);
    }
}

