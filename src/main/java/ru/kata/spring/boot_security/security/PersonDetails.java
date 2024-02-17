package ru.kata.spring.boot_security.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.models.Persson;

import java.util.Collection;

public class PersonDetails implements UserDetails {

    private final Persson persson;


    public PersonDetails(Persson persson) {
        this.persson = persson;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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

    public Persson getPersson(){
        return this.persson;
    }
}
