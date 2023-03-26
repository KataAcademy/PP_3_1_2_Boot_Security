package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    private final RoleService roleService;

    public UserController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String showUserInfo(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Set<String> rolesUtil = AuthorityUtils.authorityListToSet(user.getAuthorities());
        Set<String> totalRolesUtil = AuthorityUtils.authorityListToSet(roleService.findAll());
        Set<String> roles = new HashSet<>();
        for (String role : rolesUtil) {
            roles.add(role.replace("ROLE_", ""));
        }
        model.addAttribute("user", user);
        model.addAttribute("userRoles", roles);
        model.addAttribute("totalRoles", totalRolesUtil);
        return "/user";
    }
}
