package ru.kata.spring.boot_security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.models.Person;
import ru.kata.spring.boot_security.security.PersonDetails;
import ru.kata.spring.boot_security.services.AdminService;
import ru.kata.spring.boot_security.services.RoleService;
import ru.kata.spring.boot_security.until.PersonValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * Контроллер, отвечающий за управление пользователями администратором.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final RoleService roleService;
    private final PersonValidator personValidator;

    /**
     * Конструктор контроллера.
     *
     * @param adminService     Сервис для работы с пользователями.
     * @param roleService      Сервис для работы с ролями.
     * @param personValidator  Валидатор для объектов Person.
     */
    @Autowired
    public AdminController(AdminService adminService, RoleService roleService, PersonValidator personValidator) {
        this.adminService = adminService;
        this.roleService = roleService;
        this.personValidator = personValidator;
    }

    /**
     * Получает страницу со списком всех пользователей.
     *
     * @param model      Модель для передачи данных в представление.
     * @param principal  Объект Principal для получения информации об аутентифицированном пользователе.
     * @return Строка с именем представления "admin/users".
     */
    @GetMapping("/users")
    public String getAllUsers(Model model, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("personDetails", personDetails);
        Person person = adminService.findUserByUserName(principal.getName());
        model.addAttribute("person", person);
        List<Person> personList = adminService.getAllUsers();
        model.addAttribute("personList", personList);
        return "admin/users";
    }

    /**
     * Удаляет пользователя по ID и перенаправляет на страницу со списком пользователей.
     *
     * @param id ID пользователя для удаления.
     * @return Строка с адресом перенаправления "/admin/users".
     */
    @GetMapping("admin/removeUser")
    public String removeUser(@RequestParam("id") Long id) {
        adminService.removeUser(id);
        return "redirect:/admin/users";
    }

    /**
     * Получает форму редактирования пользователя по ID.
     *
     * @param model Модель для передачи данных в представление.
     * @param id    ID пользователя для редактирования.
     * @return Строка с именем представления "admin/userUpdate".
     */
    @GetMapping("/admin/updateUser")
    public String getEditUserForm(Model model, @RequestParam("id") Long id) {
        model.addAttribute("person", adminService.findOneById(id));
        model.addAttribute("roles", roleService.getRoles());
        return "admin/userUpdate";
    }

    /**
     * Обрабатывает форму редактирования пользователя и перенаправляет на страницу со списком пользователей.
     *
     * @param person         Объект Person с данными пользователя.
     * @param roles          Список ролей пользователя.
     * @param bindingResult  Результат валидации данных пользователя.
     * @return Строка с адресом перенаправления "/admin/users".
     */
    @PostMapping("/updateUser")
    public String postEditUserForm(@ModelAttribute("person") @Valid Person person,
                                   @RequestParam(value = "roles", required = false) List<String> roles,
                                   BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/users";
        }
        adminService.updateUser(person, roles);
        return "redirect:/admin/users";
    }
}
