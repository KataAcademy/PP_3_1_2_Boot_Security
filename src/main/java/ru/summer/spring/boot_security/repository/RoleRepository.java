package ru.summer.spring.boot_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.summer.spring.boot_security.model.Role;

/**
 * Репозиторий для работы с сущностями Role в базе данных.
 * Наследуется от JpaRepository для использования встроенных методов работы с базой данных.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Находит роль по ее имени.
     *
     * @param name Имя роли, которую нужно найти.
     * @return Объект Role, соответствующий указанному имени.
     */
    Role findByName(String name);

}
