package ru.kata.spring.boot_security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.models.Person;
import ru.kata.spring.boot_security.services.RegistrationService;
import ru.kata.spring.boot_security.until.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonValidator personValidator;

    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
    }

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }



    @GetMapping("registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person,
                                      BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()) {
            return "/registration";
        }

        registrationService.registerUser(person);

        return "redirect:/auth/login";
    }
}
