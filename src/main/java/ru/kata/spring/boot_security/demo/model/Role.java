package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
//    todo здесь тоже исправить импорты
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rolename", unique = true)
    @NotNull
    private String rolename;

    public Role() {

    }
    public Role(String rolename) {
        this.rolename = rolename;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return getRolename();
    }
}
