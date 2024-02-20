package ru.kata.spring.boot_security.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.security.PersonDetails;

/**
 * Контроллер, отвечающий за отображение информации о пользователе.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * Отображает страницу с информацией о текущем пользователе.
     *
     * @param model Модель для передачи данных в представление.
     * @return Строка с именем представления "user/userPage".
     */
    @GetMapping("")
    public String showUserInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("personDetails", personDetails);
        return "user/userPage";
    }
}
