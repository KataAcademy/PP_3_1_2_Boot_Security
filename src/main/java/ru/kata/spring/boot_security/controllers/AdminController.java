package ru.kata.spring.boot_security.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.models.Person;
import ru.kata.spring.boot_security.services.AdminService;
import ru.kata.spring.boot_security.services.RoleService;
import ru.kata.spring.boot_security.until.PersonValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final RoleService roleService;
    private final PersonValidator personValidator;

    @Autowired
    public AdminController(AdminService adminService, RoleService roleService, PersonValidator personValidator) {
        this.adminService = adminService;
        this.roleService = roleService;
        this.personValidator = personValidator;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model, Principal principal) {
        Person person = adminService.findUserByUserName(principal.getName());
        System.out.println(person);
        model.addAttribute("person", person);
        List<Person> personList = adminService.getAllUsers();
        System.out.println(personList);
        model.addAttribute("personList", personList);
        return "admin/users";
    }


    @GetMapping("admin/removeUser")
    public String removeUser(@RequestParam("id") Long id) {
        adminService.removeUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/updateUser")
    public String getEditUserForm(Model model, @RequestParam("id") Long id) {
        model.addAttribute("person", adminService.findOneById(id));
        return "admin/userUpdate";
    }


    @PostMapping("/updateUser")
    public String postEditUserForm(@ModelAttribute("person") @Valid Person person,
                                   BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()) {
            return "/registration";
        }

        adminService.updateUser(person);
        return "redirect:/admin/users";
    }
}
