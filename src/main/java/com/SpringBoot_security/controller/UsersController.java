package com.SpringBoot_security.controller;

import com.SpringBoot_security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.SpringBoot_security.model.User;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UsersController {

    private final UserService innerUserService;

    @GetMapping("/admin")
    public String printUsers(ModelMap model, RedirectAttributes redirectAttributes) {
        model.addAttribute("usersList", innerUserService.getUsersList());
        log.info("UsersController: Users printed");
        if (redirectAttributes.getFlashAttributes().containsKey("userRemoveErrMsg")) {
            String userRemoveErrMsg = (String) model.getAttribute("userRemoveErrMsg");
            model.addAttribute("userRemoveErrMsg", userRemoveErrMsg);
            log.info("UsersController: Model addAttribute added");
        }
        return "admin";
    }

    @PostMapping("/admin/add")
    public String addNewUser(@ModelAttribute("user") User formUser) {
        log.info(formUser.getFirstName());
        log.info(formUser.getLastName());
        log.info(formUser.getEmail());
        innerUserService.addUserToDatabase(formUser);
        return "redirect:/admin";
    }

    @GetMapping("/admin/remove/{id}")
    public String removeUserById(@PathVariable long id, RedirectAttributes redirectAttributes) {
        log.info("UsersController: UsersController: User removing process started");
        try {
            innerUserService.removeUserById(id);
        } catch (IllegalArgumentException exception) {
            log.info("UsersController: UsersController: IllegalArgumentException thrown");
            redirectAttributes.addFlashAttribute("userRemoveErrMsg", "User with id " + id + " does not exist");
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/updateUserData")
    public String editUserData(@RequestBody User fetchUserData) {
        log.info("UsersController: Data start fetched");
        log.info("UsersController: Request body: " + fetchUserData);
        log.info(String.valueOf(fetchUserData.getId()));
        log.info(fetchUserData.getFirstName());
        log.info(fetchUserData.getLastName());
        log.info(fetchUserData.getEmail());
        innerUserService.editUserData(fetchUserData);
        log.info("UsersController: Data fetched");
        return "redirect:/admin";
    }
}