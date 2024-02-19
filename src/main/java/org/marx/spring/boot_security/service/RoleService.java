package org.marx.spring.boot_security.service;

import org.marx.spring.boot_security.model.Role;

import java.util.List;

public interface RoleService {
    void save(Role role);

    List<Role> findAll();
}