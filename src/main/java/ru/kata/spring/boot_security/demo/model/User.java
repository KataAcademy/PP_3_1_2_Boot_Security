package ru.kata.spring.boot_security.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "firstname")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я ]+$", message = "Не должно содержать цифр и спецсимволов")
    private String firstname;
    @Column(name = "lastname")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я ]+$", message = "Не должно содержать цифр и спецсимволов")
    private String lastname;
    @Column(name = "email")
    @Email(message = "Неверный формат электропочты")
    private String email;
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
