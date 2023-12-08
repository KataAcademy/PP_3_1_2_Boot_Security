package ru.summer.spring.boot_security.service;

import ru.summer.spring.boot_security.model.User;

import java.util.List;

/**
 * Сервисный интерфейс для работы с пользователями (User).
 */
public interface UserService {

    /**
     * Добавляет нового пользователя в базу данных.
     *
     * @param user Объект User, который нужно добавить в базу данных.
     */
    void add(User user);

    /**
     * Обновляет существующего пользователя в базе данных.
     *
     * @param user Объект User, который нужно обновить в базе данных.
     */
    void update(User user);

    /**
     * Удаляет пользователя из базы данных по его ID.
     *
     * @param id ID пользователя, которого нужно удалить из базы данных.
     */
    void delete(Long id);

    /**
     * Возвращает список всех пользователей из базы данных.
     *
     * @return Список объектов User.
     */
    List<User> getAllUsers();

    /**
     * Возвращает строку, содержащую все роли пользователя.
     *
     * @param user Объект User, роли которого нужно получить.
     * @return Строка, содержащая все роли пользователя.
     */
    String getUserRoles(User user);

    /**
     * Добавляет роль к пользователю.
     *
     * @param roleName Имя роли, которую нужно добавить пользователю.
     * @param user Объект User, которому нужно добавить роль.
     */
    void addRoleToUser(String roleName, User user);

    /**
     * Находит пользователя по его имени.
     *
     * @param username Имя пользователя, которого нужно найти.
     * @return Объект User, соответствующий указанному имени пользователя.
     */
    User findByUsername(String username);

    /**
     * Находит пользователя по его ID.
     *
     * @param id ID пользователя, которого нужно найти.
     * @return Объект User, соответствующий указанному ID пользователя.
     */
    User findById(Long id);

}
