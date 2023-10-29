package ru.kata.spring.boot_security.demo.validators;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        User user = (User) target;

        try {
            userService.loadUserByUsername(user.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }

        errors.rejectValue(
                "username",
                "",
                "Пользователь уже существует"
        );
    }
}
