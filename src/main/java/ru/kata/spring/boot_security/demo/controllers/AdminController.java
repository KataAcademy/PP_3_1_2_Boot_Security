package ru.kata.spring.boot_security.demo.controllers;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;


import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/admin") public class AdminController {
    private final UserService   userService;
    private final RoleService   roleService;

    @Autowired
    public AdminController(UserService userService,
                           RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping() public String getAllUsers(Model model) {
        model.addAttribute("currentUser", getPrincipal());
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("isOwnerAdmin", true);
        return "admin/index";
    }

    @GetMapping("/create-user")
    public String createUserPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("currentUser", getPrincipal());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("isOwnerAdmin", true);
        return "admin/create-user";
    }
    @PostMapping("/create-user")
    public String createUser(@Valid User user,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            /*model.addAttribute("currentUser", getPrincipal());
            model.addAttribute("error", result.getAllErrors());
            model.addAttribute("allRoles", roleService.getAllRoles());*/
            model.addAttribute("error", result.getAllErrors());
            return "redirect:/admin";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit-user")
    public String editUserPage(@RequestParam(name = "id") Long id,
                                  Model model) {
        model.addAttribute("currentUser", getPrincipal());
        model.addAttribute("user", userService.showUserDetails(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("isOwnerAdmin", true);
        return "admin/edit-user";
    }

    @PostMapping("/edit-user")
    public String editUser(@RequestParam(name = "id") Long id,
                           @Valid User user,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            model.addAttribute("currentUser", getPrincipal());
            model.addAttribute("error", result.getAllErrors());
            model.addAttribute("isOwnerAdmin", true);
            return "admin/edit-user";
        }
        userService.updateUserDetails(id, user);
        return "redirect:/admin";
    }



    @GetMapping("/delete-user")
    public String deleteUserPage(@RequestParam(name = "id") Long id,
                                 Model model) {
        model.addAttribute("user", userService.showUserDetails(id));
        model.addAttribute("currentUser", getPrincipal());
        model.addAttribute("isOwnerAdmin", true);
        return "admin/delete-user";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam(name = "id") Long id,
                             @RequestParam(name = "cancel", required = false) boolean cancel) {
        if (!cancel) {
            userService.deleteUser(id);
        }
        return "redirect:/admin";
    }

    public User getPrincipal() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByUsername(userDetails.getUsername());
    }
}
