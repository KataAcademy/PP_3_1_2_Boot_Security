package ru.kata.spring.boot_security.demo.validators;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;

@Component
public class UserValidator implements Validator {

    private final LameUserDetailsService lameUserDetailsService;

    @Autowired
    public UserValidator(LameUserDetailsService lameUserDetailsService) {
        this.lameUserDetailsService = lameUserDetailsService;
    }

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        User user = (User) target;

        try {
            lameUserDetailsService.loadUserByUsername(user.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }

        errors.rejectValue("username","", "Человек с таким именем пользователя уже существует");

    }
}
