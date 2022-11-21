package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }


    @GetMapping("admin/users/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "show";
    }

    @GetMapping("admin/users/new")
    public String addUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("admin/users/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUser(id));
        return "edit";
    }

    @PatchMapping("admin/users/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") int id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("admin/users/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
