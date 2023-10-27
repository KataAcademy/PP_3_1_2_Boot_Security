package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.configs.SuccessUserHandler;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserServiceImp;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImp userService;
    private final SuccessUserHandler successUserHandler;

    @Autowired
    public AdminController(UserServiceImp userService, SuccessUserHandler successUserHandler) {
        this.userService = userService;
        this.successUserHandler = successUserHandler;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("roles", successUserHandler.getRolesAsStrings());
        return "admin/all-users";
    }

    @GetMapping("/user-details")
    public String showUserDetails(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", successUserHandler.getRolesAsStrings());
        return "admin/user-details";
    }

    @PostMapping("/admin/user-details")
    public String editUserDetails(@Valid User user,
                                  BindingResult result,
                                  Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", successUserHandler.getRolesAsStrings());
            model.addAttribute("error", result.getAllErrors());
            return "admin/user-details";
        }

        model.addAttribute("roles", successUserHandler.getRolesAsStrings());
        userService.updateUserDetails(user);

        return "redirect:/admin/all-users";
    }
}
