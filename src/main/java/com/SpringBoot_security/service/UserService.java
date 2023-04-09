package com.SpringBoot_security.service;


import com.SpringBoot_security.model.User;

import java.util.List;

public interface UserService {
    void addUserToDatabase(User user);
    void removeUserById(long id);
    void editUserData(User user);
    List<User> getUsersList();
}
