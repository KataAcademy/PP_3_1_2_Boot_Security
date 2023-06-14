package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;


import java.util.List;

@Service
@Transactional
public class RoleService {
    @Autowired
   private RoleRepository roleRepository;

    public void saveRole(Role role){
        roleRepository.save(role);
    }
    @Transactional(readOnly = true)
    public Role getRoleById(Long id) {
        return roleRepository.getById(id);
    }
    @Transactional(readOnly = true)
    public List<Role> getRolesList() {
        return roleRepository.findAll();
    }
}