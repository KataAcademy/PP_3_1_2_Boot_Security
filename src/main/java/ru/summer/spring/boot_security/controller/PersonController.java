package ru.summer.spring.boot_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.summer.spring.boot_security.model.Person;
import ru.summer.spring.boot_security.service.PersonService;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class PersonController {

    private final PersonService personService;


    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = "/admin")
    public String printUsers(Model model) {
        Map<Person, String> usersWithRoles = personService
                .getAllUsers()
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        personService::getRolesAsString
                ));
        model.addAttribute("usersWithRoles", usersWithRoles);
        return "users";
    }

    @PostMapping(value = "/admin/add")
    public String addUser(@RequestParam String username,
                          @RequestParam String lastname,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String role) {

        Person person = new Person(username, lastname, email, password);
        personService.add(person);
        personService.addRoleToPerson(role, person);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/update")
    public String updateUser(@RequestParam Long id,
                             @RequestParam String username,
                             @RequestParam String lastname,
                             @RequestParam String email,
                             @RequestParam String password) {
        Person user = new Person(username, lastname, email, password);
        user.setId(id);
        personService.update(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/delete")
    public String deleteUser(@RequestParam Long id) {
        personService.delete(id);
        return "redirect:/admin";
    }
}
