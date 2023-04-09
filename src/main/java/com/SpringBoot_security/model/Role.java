package com.SpringBoot_security.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Setter
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull private String groupName;

    @ManyToMany(mappedBy = "roles")
    @Getter
    private Set<User> users = new HashSet<>();

    @Override
    public String getAuthority() {
        return groupName;
    }
}
