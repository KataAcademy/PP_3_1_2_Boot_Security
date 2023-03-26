package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String index(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<User> users = userService.findAll();
        Set<String> userRolesUtil = AuthorityUtils.authorityListToSet(user.getAuthorities());
        System.out.println(userRolesUtil);
        Set<String> totalRolesUtil = AuthorityUtils.authorityListToSet(roleService.findAll());
        System.out.println(totalRolesUtil);
        Set<String> userRoles = new HashSet<>();
        for (String role : userRolesUtil) {
            userRoles.add(role.replace("ROLE_", ""));
        }
        Set<String> totalRoles = new HashSet<>();
        for (String role : totalRolesUtil) {
            totalRoles.add(role.replace("ROLE_", ""));
        }
        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("userRoles", userRoles);
        System.out.println(userRoles);
        model.addAttribute("roles", totalRoles);
        System.out.println(totalRoles);
        return "/admin";
    }

    @PatchMapping("/{id}")
    public String update(@RequestParam(name = "stringRole", required = false) String role, @ModelAttribute("user") User user) {
        userService.saveOrUpdate(user, role);
        return "redirect:/admin";
    }

    @PostMapping
    public String create(@RequestParam(name = "stringRole") String role, @ModelAttribute("user") User user) {
        userService.saveOrUpdate(user, role);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@ModelAttribute("user") User user) {
        userService.deleteById(user.getId());
        return "redirect:/admin";
    }
}
