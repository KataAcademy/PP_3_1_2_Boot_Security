package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showUsers(Model model) {
        List<User> users = userService.allUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/new")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "new-user";
    }
    @GetMapping("/edit")
    public String edit(@RequestParam(name = "id") long id, Model model){
        model.addAttribute("user", userService.getById(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors());
            return "edit";
        }
        userService.saveUser(user);
        return "redirect:/";
    }
    @PostMapping("/new")
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors());
            return "new-user";
        }
        userService.saveUser(user);
        return "redirect:/";
    }
    @GetMapping("/delete")
    public String showDeleteConfirmation(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "delete";
    }
    @PostMapping("/delete")
    public String delete(@RequestParam(name = "id") long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
