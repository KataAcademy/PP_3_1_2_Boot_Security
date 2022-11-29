package ru.kata.spring.boot_security.demo.model;

import javax.security.auth.Subject;
import java.security.Principal;

public class Admin implements Principal {

    private String name;

    private String[] role;



    public Admin(String name) {
        this.name = name;
        this.role = new String[] {"ROLE_ADMIN", "ROLE_USER"};
    }

    @Override
    public String getName() {
        return name;
    }

    public String getRole() {
        return "ADNIM USER";
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }



}
