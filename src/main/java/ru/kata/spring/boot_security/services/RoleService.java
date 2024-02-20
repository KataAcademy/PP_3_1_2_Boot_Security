package ru.kata.spring.boot_security.services;



import ru.kata.spring.boot_security.models.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getRoles();

    Role findByStringId(String sid);

    Role findById(Long id);

    Role findByName(String name);

    void saveRole(Role role);

}
