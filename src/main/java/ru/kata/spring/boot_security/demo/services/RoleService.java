package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;


public interface RoleService {

    List<Role> getListRoles();
}

