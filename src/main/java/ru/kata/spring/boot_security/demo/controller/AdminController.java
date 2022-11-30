package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Admin;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;



@Controller
@RequestMapping("admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    AdminController(UserService userService, RoleService roleService, RoleService roleService1) {
        this.userService = userService;
        this.roleService = roleService1;
    }

    @GetMapping()
    public String showAdminPage(@AuthenticationPrincipal User user, Model model, Principal principal) {
       Admin admin = new Admin(principal.getName());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("admin", admin);
        model.addAttribute("roles", roleService.getRoles());
        return "admin/test2";
    }

    @PutMapping("/{id}/update")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") Long id) {
        System.out.println(user.getUsername());
        System.out.println(user.getAge());
        System.out.println(user.getEmail());
        System.out.println(user.getSurname());
        System.out.println(user.getRoles());
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        System.out.println(user.toString());
        System.out.println(user.getUsername());
        System.out.println(user.getAge());
        userService.saveUser(user);
        return "redirect:/admin";
    }



}
