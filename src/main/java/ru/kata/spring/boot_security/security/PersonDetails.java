package ru.kata.spring.boot_security.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.models.Person;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class PersonDetails implements UserDetails {

    private final Person persson;


    public PersonDetails(Person persson) {
        this.persson = persson;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return persson.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNameOfRole()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.persson.getPassword();
    }

    @Override
    public String getUsername() {
        return this.persson.getFirstName();
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
        return true;
    }

    public Person getPersson(){
        return this.persson;
    }
}
