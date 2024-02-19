package org.marx.spring.boot_security.service;

import org.marx.spring.boot_security.model.Role;
import org.marx.spring.boot_security.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}