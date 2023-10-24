package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDaoImp;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {
    private final RoleDaoImp roleDao;
    private final RoleRepository roleRep;

    @Autowired
    public RoleServiceImp(RoleDaoImp roleDao, RoleRepository roleRep) {
        this.roleDao = roleDao;
        this.roleRep = roleRep;
    }

    @Override
    public List<Role> allRoles() {
        return null;
    }

    @Override
    public Role findRole(Long id) {
        return null;
    }

    @Override
    public void saveRole(Role role) {

    }

    @Override
    public void deleteRole(Long id) {

    }
}
