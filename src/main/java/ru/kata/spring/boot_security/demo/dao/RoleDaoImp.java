package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImp implements RoleDao {
    @PersistenceContext
    private EntityManager em;
    @Override
    public List<Role> allRoles() {
        return em
                .createQuery("select r from Role r", Role.class)
                .getResultList();
    }

    @Override
    public Role findRoleById(Long id) {
        return em.find(Role.class, id);
    }

    @Override
    public void saveRole(Role role) {
        em.merge(role);
    }

    @Override
    public void deleteRole(Long id) {
        em.remove(findRoleById(id));
    }
}
