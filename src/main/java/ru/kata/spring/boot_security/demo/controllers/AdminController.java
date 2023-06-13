package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    RoleRepository roleRepository;


    @GetMapping()
    public String allUsers(Model model) {
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("user", new User());
        return "admin/users";
    }

    @GetMapping("/{id}")
    public String user(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "admin/user";
    }


    @GetMapping("/{id}/edit")
    public String showEditUserPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        List<Role> allRoles = roleRepository.findAll();
        model.addAttribute("allRoles", allRoles);
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User editedUser) {
        userService.saveUser(editedUser);
        return "redirect:/admin/";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user, Model model) {
        List<Role> allRoles = roleRepository.findAll();
        model.addAttribute("allRoles",allRoles);

        return "redirect:/admin";
    }


    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


}



