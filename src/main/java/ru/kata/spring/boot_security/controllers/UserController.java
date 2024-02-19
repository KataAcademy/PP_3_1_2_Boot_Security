package ru.kata.spring.boot_security.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.models.Person;
import ru.kata.spring.boot_security.security.PersonDetails;

@Controller
@RequestMapping("/user")
public class UserController {

//    @GetMapping("/user")
//    public String sayHello(){
//        return "hello";
//    }


    @GetMapping("")
    public String showUserInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("personDetails", personDetails);
        return "user/userPage";
    }
}
