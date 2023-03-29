package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String index(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Set<Role> totalRoles = roleService.findAll();
        List<User> allUsers = userService.findAll();
        model.addAttribute("user",user);
        model.addAttribute("totalRoles", totalRoles);
        model.addAttribute("allUsers",allUsers);
        return "/admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user) {
        userService.saveOrUpdate(user);
        return "redirect:/admin";
    }

    @PostMapping
    public String create(@ModelAttribute("user") User user) {
        userService.saveOrUpdate(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@ModelAttribute("user") User user) {
        userService.deleteById(user.getId());
        return "redirect:/admin";
    }
}
