package ru.kata.spring.boot_security.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/restAdmin")
public class AdminRestController {
    private final UserService userService;


    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.allUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<User> getUserDetails(@RequestParam(name = "id") Long id) {
        User u = userService.showUserDetails(id);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> editUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /*@PostMapping
    public ResponseEntity<User> deleteUser(@RequestParam(name = "id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<User> getUserDetails(@RequestParam(name = "id") Long id) {
        User user = userService.showUserDetails(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }*/

}
