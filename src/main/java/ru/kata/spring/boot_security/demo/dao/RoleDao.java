package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {

    void addRole(Role role);

    Role getRoleByName(String name);

    Set<Role> getAllRoles();

    public Set<Role> getRolesByName(Set<Role> roles);
}