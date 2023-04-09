package com.SpringBoot_security.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.SpringBoot_security.model.User;

import java.util.List;

@Repository("userDaoImp")
@Slf4j
public class UserDaoImp implements UserDao {

    private final EntityManager innerEntityManager;

    @Autowired
    public UserDaoImp(EntityManagerFactory entityManagerFactory) {
        innerEntityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public void addUserToDatabase(User user) {
        innerEntityManager.getTransaction().begin();
        innerEntityManager.persist(user);
        innerEntityManager.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) throws IllegalArgumentException {
        innerEntityManager.getTransaction().begin();
        User user = innerEntityManager.find(User.class, id);
        if (user != null) {
            innerEntityManager.remove(user);
            innerEntityManager.getTransaction().commit();
        } else {
            log.info("UserDao: User is null");
            innerEntityManager.getTransaction().rollback();
            throw new IllegalArgumentException("User with id " + id + " does not exist");
        }
    }

    @Override
    public void editUserData(User user) {
        innerEntityManager.getTransaction().begin();
        User existingUser = innerEntityManager.find(User.class, user.getId());
        if (existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            innerEntityManager.merge(existingUser);
        }
        innerEntityManager.getTransaction().commit();
    }

    @Override
    public List<User> getUsersList() {
        TypedQuery<User> query = innerEntityManager
                .createQuery("from User", User.class);
        return query.getResultList();
    }
}
