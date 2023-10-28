package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.configs.SuccessUserHandler;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleServiceImp;
import ru.kata.spring.boot_security.demo.services.UserServiceImp;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImp userService;

    private final RoleServiceImp roleService;
    private final SuccessUserHandler successUserHandler;

    @Autowired
    public AdminController(UserServiceImp userService, RoleServiceImp roleService, SuccessUserHandler successUserHandler) {
        this.userService = userService;
        this.roleService = roleService;
        this.successUserHandler = successUserHandler;
    }

    @GetMapping()
    public String indexPage(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "admin/index";
    }

    /*@GetMapping("/edit-user")
    public String showUserDetails(@RequestParam(name = "id") Long id,
                                  Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/edit-user";
    }

    @PostMapping("/edit-user")
    public String editUserDetails(@Valid @ModelAttribute("user") User user,
                                  BindingResult result,
                                  Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors());
            return "admin/edit-user";
        }
        userService.updateUserDetails(user);
        return "redirect:/admin";
    }*/

    @GetMapping("/delete-user")
    public String deleteUserPage(@RequestParam(name = "id") Long id,
                                 Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/delete-user";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam(name = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    /*@GetMapping("/create-user")
    public String newUserPage(@ModelAttribute("user") User user) {
        return "admin/create-user";
    }*/

    /*@PostMapping("/create-user")
    public String createUser(@Valid @ModelAttribute("user") User user,
                             @RequestParam(name = "isUser", required = false) boolean isUserChecked,
                             @RequestParam(name = "isAdmin", required = false) boolean isAdminChecked,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors());
            return "admin/create-user";
        }
        Set<Role> roles = new HashSet<>();
        if (isAdminChecked) {
            roles.add(roleService.getRoleByName("ADMIN"));
        }

        if (isUserChecked) {
            roles.add(roleService.getRoleByName("USER"));
        }

        System.out.println(user.toString());
        userService.saveUser(user, roles);
        return "redirect:/admin";
    }*/

    @GetMapping("/create-or-edit-user")
    public String createOrEditUserPage(Model model,
                                       @RequestParam(name = "id", required = false) Long id) {

        if (id == null) {
            model.addAttribute("user", new User());
        } else {
            model.addAttribute("user", userService.getUserById(id));
        }
        return "admin/create-or-edit-user";
    }

    @PostMapping("/create-or-edit-user")
    public String createOrEditUser(@Valid @ModelAttribute(name = "user") User user,
                                   BindingResult result,
                                   Model model,
                                   @RequestParam(name = "isUser", required = false) boolean isUserChecked,
                                   @RequestParam(name = "isAdmin", required = false) boolean isAdminChecked,
                                   @RequestParam(name = "id", required = false) Long id) {
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors());
            return "admin/create-or-edit-user";
        }
        System.out.println(user.getId());
        if (user.getId() == null) {
            Set<Role> roles = new HashSet<>();
            if (isAdminChecked) {
                roles.add(roleService.getRoleByName("ADMIN"));
            }

            if (isUserChecked) {
                roles.add(roleService.getRoleByName("USER"));
            }

            System.out.println(user.toString());
            userService.saveUser(user, roles);
        } else {
            System.out.println(user.toString());
            userService.updateUserDetails(user);
        }
        return "redirect:/admin";
    }
}
