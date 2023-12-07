package ru.summer.spring.boot_security.service;

import org.springframework.stereotype.Service;
import ru.summer.spring.boot_security.model.Role;
import ru.summer.spring.boot_security.repository.RoleRepository;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
