package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private RoleService roleService;

    @GetMapping()
    public String allUsers(Model model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("principal", principal );
        model.addAttribute("users", userServiceImpl.allUsers());
        model.addAttribute("user", new User());
        List<Role> allRoles = roleService.getRolesList();
        model.addAttribute("allRoles", allRoles);
        return "admin/users";
    }

    @GetMapping("/{id}")
    public String user(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userServiceImpl.findUserById(id));
        List<Role> allRoles = roleService.getRolesList();
        model.addAttribute("allRoles", allRoles);
        return "admin/user";
    }


    @GetMapping("/{id}/edit")
    public String showEditUserPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userServiceImpl.findUserById(id));
        List<Role> allRoles = roleService.getRolesList();
        model.addAttribute("allRoles", allRoles);
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User editedUser, BindingResult bindingResult, Model model) {
        List<Role> allRoles = roleService.getRolesList();
        model.addAttribute("allRoles", allRoles);
        if (bindingResult.hasErrors()) {
            return "/admin/edit";
        }
        userServiceImpl.saveUser(editedUser);
        return "redirect:/admin/";
    }

    @PostMapping()
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        model.addAttribute("users", userServiceImpl.allUsers());
        List<Role> allRoles = roleService.getRolesList();
        model.addAttribute("allRoles", allRoles);
        if (bindingResult.hasErrors()) {
            return "/admin/users";
        }
        userServiceImpl.saveUser(user);
        return "redirect:/admin/";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/admin/";
    }


}



