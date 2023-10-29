package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.validators.UserValidator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;

@Controller
@RequestMapping("/admin") public class AdminController {
    private final UserService   userService;
    private final RoleService   roleService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService,
                           RoleService roleService,
                           UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @GetMapping() public String getAllUsers(Model model) {
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("isOwnerAdmin", true);
        return "admin/index";
    }

    @GetMapping("/edit-user")
    public String userDetailsPage(@RequestParam(name = "id") Long id,
                                  Model model) {
        model.addAttribute("user", userService.showUserDetails(id));
        model.addAttribute("isOwnerAdmin", true);
        return "admin/edit-user";
    }

    @PostMapping("/edit-user")
    public String editUser(@RequestParam(name = "id") Long id,
                           @Valid User user,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors());
            model.addAttribute("isOwnerAdmin", true);
            return "admin/edit-user";
        }
        userService.updateUserDetails(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/create-user")
    public String createUserPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("isOwnerAdmin", true);
        return "admin/create-user";
    }

    @PostMapping("/create-user")
    public String createUser(@Valid @ModelAttribute("user") User user,
                             @RequestParam(name = "isUser", required = false) boolean isUserChecked,
                             @RequestParam(name = "isAdmin", required = false) boolean isAdminChecked,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors());
            return "admin/create-user";
        }
        Collection<Role> roles = new HashSet<>();
        if (isAdminChecked) {
            roles.add(roleService.getByName("ROLE_ADMIN"));
        }

        if (isUserChecked) {
            roles.add(roleService.getByName("ROLE_USER"));
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete-user")
    public String deleteUserPage(@RequestParam(name = "id") Long id,
                                 Model model) {
        model.addAttribute("user", userService.showUserDetails(id));
        model.addAttribute("isOwnerAdmin", true);
        return "admin/delete-user";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam(name = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
