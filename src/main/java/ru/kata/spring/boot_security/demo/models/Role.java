package ru.kata.spring.boot_security.demo.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
@RequiredArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List< User > users;

    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
