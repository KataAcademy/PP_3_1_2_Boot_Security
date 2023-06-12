package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;


    @GetMapping()
    public String allUsers(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "admin";
    }

    @GetMapping("/{id}")
    public String user(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "admin/user";
    }

    @GetMapping("/{id}/edit")
    public String showEditUserPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String editUser(@ModelAttribute("user") User editedUser) {
        userService.saveUser(editedUser);
        return "redirect:/admin/users";
    }


//
//    @DeleteMapping("/{id}")
//    public String deleteUser(@PathVariable("id") Long id) {
//        userService.deleteUser(id);
//        return "redirect:/admin";
//    }
//
//    @PostMapping()
//    public String addUser(@ModelAttribute("user") User user) {
//        userService.saveUser(user);
//
//        return "redirect:/admin";
// }


}



