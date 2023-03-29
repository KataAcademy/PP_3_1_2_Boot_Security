package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    private final RoleService roleService;

    public UserController(RoleService roleService) {
        this.roleService = roleService;
    }
    @GetMapping
    public String showUserInfo(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Set<Role> totalRoles = roleService.findAll();
        model.addAttribute("totalRoles", totalRoles);
        model.addAttribute("user", user);
        return "/user";
    }
}
