package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> allUsers() {
        return em
                .createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public User findUser(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public void saveUserDetails(User user) {
        em.merge(user);
    }

    @Override
    public void deleteUser(Long id) {
        em.remove(findUser(id));
    }
}
