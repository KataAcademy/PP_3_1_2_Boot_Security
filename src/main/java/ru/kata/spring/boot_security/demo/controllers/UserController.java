package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.LameUserDetailsService;
import ru.kata.spring.boot_security.demo.services.UserServiceImp;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final LameUserDetailsService lameUserDetailsService;

    @Autowired
    public UserController(LameUserDetailsService lameUserDetailsService) {
        this.lameUserDetailsService = lameUserDetailsService;
    }

    @GetMapping()
    public String userPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) lameUserDetailsService.loadUserByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "user";
    }
}
