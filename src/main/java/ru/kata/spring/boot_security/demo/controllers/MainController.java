package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    //Вход без аутентификации.
    @GetMapping("/")
    public String HomePage() {
        return "home";
    }

    // Вход после Аутентификации
    @GetMapping("/authenticated")
    public String pageForAuthenticatedUser(Principal principal){
        return "authenticated page " + principal.getName();
    }
}
