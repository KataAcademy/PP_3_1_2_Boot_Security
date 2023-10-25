package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.services.LameUserDetailsService;
import ru.kata.spring.boot_security.demo.services.UserServiceImp;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserServiceImp userService;
    private final LameUserDetailsService lameUserDetailsService;

    @Autowired
    public UserController(UserServiceImp userService, LameUserDetailsService lameUserDetailsService) {
        this.userService = userService;
        this.lameUserDetailsService = lameUserDetailsService;
    }

    @GetMapping()
    public String userPage(@RequestParam("id") Long id, Model model) {
        System.out.println("creating user page");
        model.addAttribute("user", userService.getUserById(id));
        return "user";
    }
}
