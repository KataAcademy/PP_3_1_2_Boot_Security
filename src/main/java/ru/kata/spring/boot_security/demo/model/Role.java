package ru.kata.spring.boot_security.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import java.util.Set;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role_name")
    private String roleName;

    @ManyToMany
    @JoinTable(name = "users", joinColumns = @JoinColumn(name = "roles_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;
}
