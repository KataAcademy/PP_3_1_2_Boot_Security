package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@RestController
public class MainController {
    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String HomePage() {
        return "home";
    }

    // Вход после Аутентификации
    @GetMapping("/authenticated")
    public String pageForAuthenticatedUser(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return "authenticated page " + principal.getName() + ", " + user.getEmail();
    }


    @GetMapping("/admin")
    public String page_only_for_admins() {
        return "admin page";
    }


    public UserService getUserService() {
        return userService;
    }
}
