package ru.summer.spring.boot_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.summer.spring.boot_security.model.User;
import ru.summer.spring.boot_security.service.UserService;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Контроллер для административных задач пользователя.
 * Доступен только для ROLE_ADMIN
 */
@Controller
public class UserAdminController {

    private final UserService userService;

    /**
     * Создает новый контроллер с указанным сервисом пользователя.
     * @param userService сервис для обработки операций с пользователем
     */
    @Autowired
    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Отображает пользователей с их ролями.
     * @return adminPanel
     */
    @GetMapping(value = "/admin")
    public String printUsers(Model model) {
        Map<User, String> usersWithRoles = userService
                .getAllUsers()
                .stream()
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toMap(
                        Function.identity(),
                        userService::getUserRoles,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));

        model.addAttribute("usersWithRoles", usersWithRoles);

        return "AdminPanel";
    }

    /**
     * Добавляет нового пользователя.
     * @return перенаправление на adminPanel
     */
    @PostMapping(value = "/admin/add")
    public String addUser(@RequestParam String username,
                          @RequestParam String lastname,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String role) {
        User user = new User(username, lastname, email, password);
        userService.add(user);
        userService.addRoleToUser(role, user);

        return "redirect:/admin";
    }

    /**
     * Обновляет существующего пользователя.
     * @return перенаправление на adminPanel
     */
    @PostMapping(value = "/admin/update")
    public String updateUser(@RequestParam Long id,
                             @RequestParam String username,
                             @RequestParam String lastname,
                             @RequestParam String email,
                             @RequestParam String password) {
        User updateUser = userService.findById(id);

        if (updateUser != null) {
            updateUser.setUsername(username);
            updateUser.setLastname(lastname);
            updateUser.setEmail(email);
            updateUser.setPassword(password);
            userService.update(updateUser);
        }

        return "redirect:/admin";
    }

    /**
     * Удаляет пользователя.
     * @return перенаправление на adminPanel
     */
    @PostMapping(value = "/admin/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.delete(id);

        return "redirect:/admin";
    }

}
