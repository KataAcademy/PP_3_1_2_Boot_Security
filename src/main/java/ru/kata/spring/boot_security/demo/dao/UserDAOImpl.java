package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<User> getUserList() {
        return entityManager.createQuery("select user from User user", User.class).getResultList();
    }

    @Override
    public User getUser(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(int id, User user) {
        User updateUser = getUser(id);
        updateUser.setUsername(user.getUsername());
        updateUser.setSurname(user.getSurname());
        updateUser.setAge(user.getAge());
        updateUser.setEmail(user.getEmail());
        entityManager.merge(updateUser);
    }

    @Override
    public void deleteUser(int id) {
        entityManager.remove(getUser(id));
    }

}