package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;


@Controller

public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAllUsers(Principal principal, Model model) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", userService.createUser());
        model.addAttribute("listRoles",roleService.getAllRoles());
        return "admin";
    }

    @GetMapping("/admin/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user";
    }

    @PostMapping("/admin/new")
    public String createUser(@ModelAttribute("newUser") User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            System.out.println("Binderr");
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @DeleteMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }


    @PutMapping ("/admin/updateUser/{id}")
    public String updateUser(@ModelAttribute("updateUser")User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }
}