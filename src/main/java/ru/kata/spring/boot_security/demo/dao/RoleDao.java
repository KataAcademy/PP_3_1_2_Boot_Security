package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDao {
    List<Role> findAll();
    Optional<Role> findByName(String name);
}
