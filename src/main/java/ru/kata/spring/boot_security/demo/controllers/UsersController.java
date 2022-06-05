package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String user(Model model, Principal principal) {
        int id = userService.getByNickName(principal.getName()).getId();
        model.addAttribute("user", userService.get(id));
        return "user";
    }

    @GetMapping("/edit")
    public String edit(Model model, Principal principal) {
        int id = userService.getByNickName(principal.getName()).getId();
        model.addAttribute("user", userService.get(id));
        return "edit";
    }

    @PatchMapping
    public String update(@ModelAttribute("user") User user, Principal principal) {
        int id = userService.getByNickName(principal.getName()).getId();
        userService.update(id, user);
        return "redirect:/";
    }

    @DeleteMapping
    public String delete(Principal principal) {
        int id = userService.getByNickName(principal.getName()).getId();
        userService.delete(id);
        return "redirect:/";
    }
}
