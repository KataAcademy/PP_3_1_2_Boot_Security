package ru.summer.spring.boot_security.service;

import ru.summer.spring.boot_security.model.Role;

/**
 * Сервисный интерфейс для работы с ролями (Role).
 */
public interface RoleService {

    /**
     * Находит роль по ее имени.
     *
     * @param name Имя роли, которую нужно найти.
     * @return Объект Role, соответствующий указанному имени.
     */
    Role findByName(String name);

}
