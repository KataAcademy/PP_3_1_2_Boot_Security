package ru.kata.spring.boot_security.demo.security;

import ru.kata.spring.boot_security.demo.models.User;

public interface UserResolver {
    User resolveUserPasswordAndRole(User user, String role);
}
