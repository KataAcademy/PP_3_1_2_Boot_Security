package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public List<Role> allRoles() {
        return roleRep.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRep.getById(id);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRep.findRoleByRoleName(roleName);
    }

    @Override
    public void saveRole(Role role) {
        roleRep.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        roleRep.deleteById(id);
    }
}
