package ru.kata.spring.boot_security.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
    //todo настроить импорты по классам
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long   id;

    @Column(name = "user_name", unique = true)
    @NotNull(message = "Can not be empty")
    private String userName;

    @Column(name = "email", unique = true)
    @Email(message = "Mismatch email pattern.")
    private String email;

    @Column(name = "password")
    @NotNull(message = "Can not be empty")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public void setId(Long id) {
        this.id = id;
    }
}
