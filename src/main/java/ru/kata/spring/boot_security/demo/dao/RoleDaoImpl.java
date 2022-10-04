package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }


    @Override
    public Role getRoleByName(String name) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", name).getSingleResult();
    }


    @Override
    public Set<Role> getAllRoles() {
        return new HashSet<>(entityManager.createQuery("from Role", Role.class)
                .getResultList());
    }

    public Set<Role> getRolesByName(Set<Role> roles){
        Set<Role> userRoles = new HashSet<>();
        for (Role role : roles) {
            userRoles.add(getRoleByName(role.getName()));
        }
        return userRoles;
    }
}