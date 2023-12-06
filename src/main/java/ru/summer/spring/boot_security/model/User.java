package ru.summer.spring.boot_security.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


/**
 * Представляет сущность User для хранения информации о пользователях в базе данных.
 * Используется для взаимодействия с таблицей "users".
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String email;

    /**
     * Создает новый экземпляр User.
     *
     * @param name    Имя пользователя
     * @param surname Фамилия пользователя
     * @param email   Электронная почта пользователя
     */
    public User(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

}
