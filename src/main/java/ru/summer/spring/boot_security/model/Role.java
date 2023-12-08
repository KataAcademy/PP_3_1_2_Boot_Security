package ru.summer.spring.boot_security.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Сущность, представляющая роль пользователя в системе безопасности.
 * Реализует интерфейс GrantedAuthority для интеграции с Spring Security.
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Role implements GrantedAuthority {

    /**
     * Идентификатор роли. Генерируется автоматически.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    /**
     * Наименование роли. Уникальное в системе.
     */
    @Column(name = "role_name", unique = true)
    private String name;

    /**
     * Возвращает наименование роли как разрешение (authority) в Spring Security.
     * @return наименование роли
     */
    @Override
    public String getAuthority() {
        return name;
    }

    /**
     * Возвращает наименование роли как строковое представление объекта Role.
     * @return наименование роли
     */
    @Override
    public String toString() {
        return name;
    }

}
