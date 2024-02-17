package ru.kata.spring.boot_security.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.security.PersonDetails;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String sayHello(){
        return "hello";
    }


    @GetMapping("user")
    public String showUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPersson());
        return "user";
    }
}
