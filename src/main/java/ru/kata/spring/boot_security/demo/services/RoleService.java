package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> allRoles();
    Role findRole(Long id);
    void saveRole(Role role);
    void deleteRole(Long id);
}
