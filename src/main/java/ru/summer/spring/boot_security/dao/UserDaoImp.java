package ru.summer.spring.boot_security.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.summer.spring.boot_security.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Реализация интерфейса UserDao.
 * Использует JPA EntityManager для взаимодействия с базой данных.
 */
@Repository
public class UserDaoImp implements UserDao {

    private final EntityManagerFactory emf;

    /**
     * Создает новый объект UserDaoImp с заданным EntityManagerFactory.
     *
     * @param emf Используется для создания EntityManager для взаимодействия с базой данных
     */
    @Autowired
    public UserDaoImp(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * Добавляет нового пользователя в базу данных.
     *
     * @param user Пользователь для добавления
     */
    @Override
    public void add(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Обновляет существующего пользователя в базе данных.
     *
     * @param user Пользователь с обновленными данными
     */
    @Override
    public void update(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Удаляет пользователя из базы данных по его ID.
     *
     * @param id ID пользователя для удаления
     */
    @Override
    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Получает список всех пользователей из базы данных.
     *
     * @return Список всех пользователей
     */
    @Override
    public List<User> getAllUsers() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM users u", User.class);
        List<User> users = query.getResultList();
        em.close();
        return users;
    }
}
