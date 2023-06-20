package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {


    @GetMapping()
    public String user(Model model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean admin = principal.getAuthorities().stream().anyMatch(p-> p.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("admin", admin);
        model.addAttribute("principal", principal);
        model.addAttribute("role", principal.getRoles());
        return "user/profile";
    }

}
