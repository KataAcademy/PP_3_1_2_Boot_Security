package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleDao {
    List<Role> allRoles();
    Role findRoleById(Long id);
    void saveRole(Role role);
    void deleteRole(Long id);
}
