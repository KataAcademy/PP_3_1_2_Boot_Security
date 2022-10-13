package ru.kata.spring.boot_security.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PageController {

    @GetMapping("/user")
    public String showUser() {
        return "user"; }


    @GetMapping("/admin")
    public String showAdmin() {
        return "admin"; }

}