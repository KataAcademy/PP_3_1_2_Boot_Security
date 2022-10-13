package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserErrorResponse;
import ru.kata.spring.boot_security.demo.util.UserNotFoundException;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api")
public class AdminRestController {

    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> showAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    public List<User> addNewUser(@RequestBody User user) {
        userService.saveUser(user);
        return userService.getAllUsers();
    }


    @PutMapping("/users")
    public List<User> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return userService.getAllUsers();
    }


    @DeleteMapping("/users/{id}")
    public List<User> deleteUser(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return userService.getAllUsers();
    }

    @GetMapping("/user")
    public User showAuthenticatedUser(Principal principal) {
        return userService.getUserByUsername(principal.getName());
    }


    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException e) {
        UserErrorResponse response = new UserErrorResponse(
                "User with this id is wasn't found!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}