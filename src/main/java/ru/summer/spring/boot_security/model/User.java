package ru.summer.spring.boot_security.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность, представляющая пользователя в системе.
 * Реализует интерфейс UserDetails для интеграции с Spring Security.
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity(name = "users")
public class User implements UserDetails {

    /**
     * Идентификатор пользователя. Генерируется автоматически.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    /**
     * Имя пользователя. Выступает как Login пользователя.
     * Должно быть уникальным в системе.
     */
    @Column(name = "user_name", unique = true)
    @NotBlank(message = "Name cannot be empty")
    private String username;

    /**
     * Фамилия пользователя.
     */
    @Column(name = "last_name")
    @NotBlank(message = "Surname cannot be empty")
    private String lastname;

    /**
     * Электронная почта пользователя.
     * Должна быть уникальной в системе
     * и соответствовать формату электронной почты.
     */
    @Column(name = "email", unique = true)
    @Email(message = "Email is not correct")
    private String email;

    /**
     * Пароль пользователя. Должен содержать не менее 4 символов.
     */
    @Column(name = "password")
    @Size(min = 4)
    private String password;

    /**
     * Состояние активации пользователя.
     */
    @Column
    @ToString.Exclude
    private boolean enabled;

    /**
     * Роли, присвоенные пользователю.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();

    /**
     * Создает новый экземпляр пользователя
     * с указанными именем, фамилией,
     * электронной почтой и паролем.
     */
    public User(String username, String lastName, String email, String password) {
        this.username = username;
        this.lastname = lastName;
        this.email = email;
        this.password = password;
        this.enabled = true;
    }

    /**
     * Возвращает коллекцию ролей, связанных с этим пользователем.
     * Этот метод необходим для интеграции с Spring Security.
     *
     * @return коллекция объектов, каждый из которых реализует GrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    /**
     * Возвращает пароль пользователя.
     *
     * @return строку, представляющую пароль пользователя
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Возвращает имя пользователя.
     *
     * @return строку, представляющую имя пользователя
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Проверяет, не истек ли срок действия учетной записи пользователя.
     * Данную функцию я не реализовывал.
     *
     * @return true, если учетная запись не истекла
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Проверяет, не заблокирована ли учетная запись пользователя.
     * Данную функцию я не реализовывал.
     *
     * @return true, если учетная запись не заблокирована
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Проверяет, не истекли ли учетные данные пользователя.
     * Данную функцию я не реализовывал.
     *
     * @return true, если учетные данные не истекли
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Проверяет, активирован ли пользователь.
     * При создании User пользователь активирован по умолчанию.
     *
     * @return true, если пользователь активирован
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
