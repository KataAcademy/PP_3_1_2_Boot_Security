package ru.summer.spring.boot_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.summer.spring.boot_security.model.User;
import ru.summer.spring.boot_security.service.UserService;


/**
 * Контроллер для обработки запросов, связанных с пользователями.
 */
@Controller
public class UserController {

    private final UserService userService;

    /**
     * Создает новый объект UserController с заданным UserService.
     *
     * @param userService Используется для выполнения операций с пользователями
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Обрабатывает GET-запросы на главную страницу.
     * Добавляет список всех пользователей в модель.
     *
     * @param model Модель для добавления атрибутов
     * @return Имя представления для отображения
     */
    @GetMapping(value = "/")
    public String printUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    /**
     * Обрабатывает POST-запросы для добавления нового пользователя.
     *
     * @param name    Имя пользователя
     * @param surname Фамилия пользователя
     * @param email   Электронная почта пользователя
     * @return Перенаправление на главную страницу
     */
    @PostMapping(value = "/add")
    public String addUser(@RequestParam String name, @RequestParam String surname, @RequestParam String email) {
        User user = new User(name, surname, email);
        userService.add(user);
        return "redirect:/";
    }

    /**
     * Обрабатывает POST-запросы для обновления существующего пользователя.
     *
     * @param id      ID пользователя
     * @param name    Новое имя пользователя
     * @param surname Новая фамилия пользователя
     * @param email   Новый адрес электронной почты пользователя
     * @return Перенаправление на главную страницу
     */
    @PostMapping(value = "/update")
    public String updateUser(@RequestParam Long id, @RequestParam String name, @RequestParam String surname, @RequestParam String email) {
        User user = new User(name, surname, email);
        user.setId(id);
        userService.update(user);
        return "redirect:/";
    }

    /**
     * Обрабатывает POST-запросы для удаления пользователя.
     *
     * @param id ID пользователя для удаления
     * @return Перенаправление на главную страницу
     */
    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.delete(id);
        return "redirect:/";
    }
}
