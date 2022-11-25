package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/test";
    }

}
