package ru.kata.spring.boot_security.demo.controller;

import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String allUsers(Model model) {
        List<User> users = userService.allUsers();
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/new-user")
    public String createUserForm(User user) {
        return "new-user";
    }

    @PostMapping("/new-user")
    public String createUser(User user) {
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteUserPage(@RequestParam(name = "id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "delete";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam(name = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editUserForm(@RequestParam(name = "id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute(user);
        return "edit";
    }

    @PostMapping("/edit")
    public String editUser(User user) {
        userService.saveUser(user);
        return "redirect:/";
    }
}
