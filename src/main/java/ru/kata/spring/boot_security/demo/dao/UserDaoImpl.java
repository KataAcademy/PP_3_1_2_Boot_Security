package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.dao.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.models.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private final EntityManager entityManager;

    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> findUserByEmailFetchRoles(String username) {
        return entityManager.createQuery("select u from User u join fetch u.roles where u.email = :username", User.class)
                .setParameter("username", username).getResultStream().findAny();
    }

    @Override
    public Set<User> findAllFetchRoles() {
        return new HashSet<>(entityManager.createQuery("select u from User u join fetch u.roles", User.class)
                .getResultList());
    }

    @Override
    public User save(User user) {
        entityManager.merge(user);
        return user;
    }

    public void deleteById(Long id) {
        entityManager.createQuery("delete from User u where u.id = :userId")
                .setParameter("userId", id)
                .executeUpdate();
    }
}
