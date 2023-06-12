package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String allUsers(Model model) {
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("user", new User());
        return "admin";
    }

    @GetMapping("/{id}/edit")
    public String getEditUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findUserById(id));
        return "editUser";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
    @PostMapping()
    public String addUser(@ModelAttribute("user")User user) {
        userService.saveUser(user);

        return "redirect:/admin";
    }

    @PatchMapping
    public String updateUser(@ModelAttribute("user")User user){
        userService.saveUser(user);
        return "redirect:/admin";
    }


}
