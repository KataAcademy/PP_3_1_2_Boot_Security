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

    @GetMapping("/get-user-details")
    public ResponseEntity<User> getUserDetails(@RequestParam(name = "id") Long id) {
        User u = userService.showUserDetails(id);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @PostMapping("/edit-user")
    public ResponseEntity<User> editUser(@RequestBody User user) {
        userService.updateUserDetails(user.getId(), user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/delete-user")
    public ResponseEntity<User> deleteUser(@RequestBody User user) {
        userService.deleteUser(user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
