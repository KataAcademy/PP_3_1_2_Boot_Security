package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoHiberImpl implements UserDao{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void removeUserById(long id) {
        entityManager.remove(getUserById(id));
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {

        entityManager.merge(user);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }


    @Override
    public User getUserByUsername(String username) {
        return entityManager.createQuery("SELECT u FROM User AS u JOIN FETCH u.roles WHERE u.username= :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}