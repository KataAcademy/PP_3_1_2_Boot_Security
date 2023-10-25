package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {
    private final RoleRepository roleRep;

    @Autowired
    public RoleServiceImp(RoleRepository roleRep) {
        this.roleRep = roleRep;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> allRoles() {
        return roleRep.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleById(Long id) {
        return roleRep.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByName(String roleName) {
        return roleRep.findRoleByRoleName(roleName);
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        roleRep.save(role);
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        roleRep.deleteById(id);
    }
}
