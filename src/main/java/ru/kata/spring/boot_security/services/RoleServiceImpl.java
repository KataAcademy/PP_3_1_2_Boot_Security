//package ru.kata.spring.boot_security.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import ru.kata.spring.boot_security.models.Role;
//import ru.kata.spring.boot_security.repositories.RoleRepository;
//
//import java.util.Set;
//
//@Service
//public class RoleServiceImpl implements RoleService {
//
//    @Autowired
//    private final RoleRepository roleRepository;
//
//    public RoleServiceImpl(RoleRepository roleRepository) {
//        this.roleRepository = roleRepository;
//    }
//
//
//    @Override
//    public Set<Role> getRoles() {
//        return roleRepository.findAll();
//    }
//
//    @Override
//    public Role findById(Long id) {
//        return roleRepository.findById(id);
//    }
//
//    @Override
//    public Role findByName(String name) {
//        return roleRepository.findByName(name);
//    }
//}
