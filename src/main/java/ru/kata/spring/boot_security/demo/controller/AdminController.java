package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Admin;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.stream.Collectors;


@Controller
@RequestMapping("admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    AdminController(UserService userService, RoleService roleService, RoleService roleService1) {
        this.userService = userService;
        this.roleService = roleService1;
    }

//    @GetMapping()
//    public String getAllUsers(Model model) {
//        model.addAttribute("users", userService.getAllUsers());
//        System.out.println("getAllUsers----------------------------");
//        return "index";
//    }
//
//    @GetMapping("/{id}")
//    public String show(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("user", userService.getUserById(id));
//        System.out.println("show----------------------------");
//        return "show";
//    }
//
//    @GetMapping("/new")
//    public String addUser(@ModelAttribute("user") User user) {
//        System.out.println("addUser----------------------------");
//        return "new";
//    }
//
//    @PostMapping()
//    public String create(@ModelAttribute("user") User user) {
//        userService.saveUser(user);
//        System.out.println("create----------------------------");
//        return "redirect:/admin/test2";
//    }

//    @GetMapping("/{id}/update")
//    public String edit(Model model, @PathVariable("id") Long id) {
//        System.out.println("edit-----------------------------------------");
//        model.addAttribute("user", userService.getUserById(id));
//        return "edit";
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") Long id) {
//        userService.deleteUserById(id);
//        System.out.println("delete------------------------------------------------------------");
//        return "redirect:/admin";
//    }
///////////////////////////////////
//    @GetMapping("/test2")
//    public String testPage(Principal principal, Model model) {
//        User user = userService.getUserByUsername(principal.getName());
//        System.out.println(user.getEmail());
//        System.out.println(user.getUsername());
//        model.addAttribute("users", userService.getAllUsers());
//        model.addAttribute("admin", userService.getUserById(user.getId()));
////        model.addAttribute("users", userService.getAllUsers());
//        return "admin/adminFirstPage";
//    }

//    @PutMapping("/users/{id}/editUser")
//    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
//        userService.updateUser(id, user);
//        System.out.println(user);
//        return "redirect:/admin/adminFirstPage";
//    }

//    @PutMapping("/{id}/update")
//    public String updateUser(@ModelAttribute("user") User user, Model model) {
//        model.addAttribute("roles", roleService.getRoles());
////        roleService.getRoles(user);
//        userService.updateUser(user.getId(), user);
//        return "redirect:/test2";
//    }

//    @GetMapping("/a")
//    public String show111AdminPage(@AuthenticationPrincipal User user, Model model, Principal principal) {
//        User user1 = new User();
//        user1.setUsername(principal.getName());
//        model.addAttribute("users", userService.getAllUsers());
//        model.addAttribute("user", user1);
//        System.out.println("-----------------------------------");
//        System.out.println(user1.getUsername());
//        model.addAttribute("roles", roleService.getRoles());
//        System.out.println(roleService.getRoles());
//        return "admin/editCnopka";
//    }

    /////////////////////////////////////////////

    @GetMapping()
    public String showAdminPage(@AuthenticationPrincipal User user, Model model, Principal principal) {
       Admin admin = new Admin(principal.getName());
        System.out.println(principal + "++++++++++ +++++++++++++++++++");
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("admin", admin);
//        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getRoles());
        System.out.println(roleService.getRoles());
        return "admin/test2";
    }

    @PutMapping("/{id}/update")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") Long id) {
        System.out.println(user.getUsername());
        System.out.println(user.getAge());
        System.out.println(user.getEmail());
        System.out.println(user.getSurname());
        System.out.println(user.getRoles());

        System.out.println("flag update---------------------------------------------");
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }





}
