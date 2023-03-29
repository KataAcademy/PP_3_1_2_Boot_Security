package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private final EntityManager entityManager;

    public RoleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    public Optional<Role> findByName(String name) {
        return entityManager.createQuery("select r from Role r where r.name=:name", Role.class)
                .setParameter("name", name).getResultList().stream().findAny();
    }
}
