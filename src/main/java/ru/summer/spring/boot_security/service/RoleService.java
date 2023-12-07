package ru.summer.spring.boot_security.service;

import ru.summer.spring.boot_security.model.Role;

public interface RoleService {

    Role findByName(String name);

}
