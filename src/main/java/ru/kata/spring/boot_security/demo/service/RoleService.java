package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    void addRole(Role role);


    Role getRoleByName(String name);

    public Set<Role> getRolesByName(Set<Role> roles);


    Set<Role> getAllRoles();
}