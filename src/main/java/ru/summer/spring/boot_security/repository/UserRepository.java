package ru.summer.spring.boot_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.summer.spring.boot_security.model.User;

/**
 * Репозиторий для работы с сущностями User в базе данных.
 * Наследуется от JpaRepository для использования встроенных методов работы с базой данных.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по его имени пользователя.
     *
     * @param username Имя пользователя, которого нужно найти.
     * @return Объект User, соответствующий указанному имени пользователя.
     */
    User findByUsername(String username);

}
