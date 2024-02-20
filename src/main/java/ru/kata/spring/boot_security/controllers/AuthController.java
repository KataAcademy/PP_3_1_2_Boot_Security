package ru.kata.spring.boot_security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.models.Person;
import ru.kata.spring.boot_security.services.RegistrationService;
import ru.kata.spring.boot_security.until.PersonValidator;

import javax.validation.Valid;

/**
 * Контроллер, отвечающий за аутентификацию и регистрацию пользователей.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonValidator personValidator;

    /**
     * Конструктор контроллера.
     *
     * @param registrationService Сервис для регистрации пользователей.
     * @param personValidator    Валидатор для объектов Person.
     */
    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
    }

    /**
     * Отображает страницу входа.
     *
     * @return Строка с именем представления "security/login".
     */
    @GetMapping("login")
    public String loginPage() {
        return "security/login";
    }

    /**
     * Отображает страницу регистрации.
     *
     * @param person Объект Person для передачи данных на страницу.
     * @return Строка с именем представления "security/registration".
     */
    @GetMapping("registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "security/registration";
    }

    /**
     * Обрабатывает форму регистрации и перенаправляет на страницу входа.
     *
     * @param person         Объект Person с данными пользователя.
     * @param bindingResult  Результат валидации данных пользователя.
     * @return Строка с адресом перенаправления "/auth/login".
     */
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person,
                                      BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "security/registration";
        }
        registrationService.registerUser(person);

        return "redirect:/auth/login";
    }
}
