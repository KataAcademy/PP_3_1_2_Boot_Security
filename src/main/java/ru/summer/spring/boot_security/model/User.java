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

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Column
    @NotBlank(message = "Surname cannot be empty")
    private String surname;

    @Column
    @Email(message = "Email is not correct")
    private String email;

    @Column
    @Size(min = 4)
    private String password;

    @Column
    private boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();

    /**
     * Создает новый экземпляр User.
     *
     * @param name    Имя пользователя
     * @param surname Фамилия пользователя
     * @param email   Электронная почта пользователя
     */
    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
