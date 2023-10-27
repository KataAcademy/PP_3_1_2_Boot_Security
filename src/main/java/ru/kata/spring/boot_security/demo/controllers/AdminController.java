package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.services.LameUserDetailsService;
import ru.kata.spring.boot_security.demo.services.UserServiceImp;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImp userService;
    private final LameUserDetailsService lameUserService;

    @Autowired
    public AdminController(UserServiceImp userService, LameUserDetailsService lameUserService) {
        this.userService = userService;
        this.lameUserService = lameUserService;
    }


}
