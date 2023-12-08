package ru.summer.spring.boot_security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.summer.spring.boot_security.model.Role;
import ru.summer.spring.boot_security.repository.RoleRepository;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
