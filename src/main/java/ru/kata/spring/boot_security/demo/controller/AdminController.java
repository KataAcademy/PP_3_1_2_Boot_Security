package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @RequestMapping(value = "/admin")
    public String adminPage(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "adminPage";
    }

    @GetMapping(value = "/admin/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "addingPage";
    }

    @PostMapping(value = "/admin/add" )
    public String postUser(@ModelAttribute("user") User user,
                           @RequestParam(required=false) String role) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getDefaultRole());

        if (role != null && role.equals("ROLE_ADMIN")) {
            roles.add(roleService.getRoleByName(role));
        }
        user.setRoles(roles);
        userService.addUser(user);

        return "redirect:/admin";
    }

    @GetMapping(value = "admin/edit/{id}")
    public String editUser(Model model, @PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        Set<Role> roles = user.getRoles();
        for (Role role: roles) {
            if (role.equals(roleService.getRoleByName("ROLE_ADMIN"))) {
                model.addAttribute("role", true);
            }
        }
        model.addAttribute("user", user);
        return "editingPage";
    }

    @PostMapping(value = "admin/edit")
    public String postEditUser(@ModelAttribute("user") User user,
                               @RequestParam(name = "roleAdmin", required=false) String role) {

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getDefaultRole());
        if (role != null && role.equals("ROLE_ADMIN")) {
            roles.add(roleService.getRoleByName(role));
        }
        user.setRoles(roles);
        userService.editUser(user);
        System.out.println(role);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


}