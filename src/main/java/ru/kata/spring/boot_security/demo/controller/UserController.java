package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.Admin;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String userPage(@AuthenticationPrincipal User user, Model model, Principal principal) {
        Admin admin = new Admin(principal.getName());
        System.out.println(principal + "++++++++++ +++++++++++++++++++");
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("admin", admin);
        Long id = userService.getUserByUsername(principal.getName()).getId();
        model.addAttribute("user", userService.getUserById(id));
        return "admin/userPage";
    }

}
