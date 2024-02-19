package org.marx.spring.boot_security.controller;

import org.marx.spring.boot_security.model.User;
import org.marx.spring.boot_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "edit";
    }
    @GetMapping
    public String showAll(Model model, @ModelAttribute("user") User user) {
        model.addAttribute("users", userService.getUserList());
        return "admin";
    }

    @PostMapping
    public String create(@ModelAttribute("admin") User user) {
        userService.create(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("admin") User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping
    public String delete(@ModelAttribute("user") User user) {
        userService.deleteById(user.getId());
        return "redirect:/admin";
    }

}