package com.SpringBoot_security.service;


import com.SpringBoot_security.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.SpringBoot_security.DAO.UserDaoImp;
import com.SpringBoot_security.model.User;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {

    private final UserDaoImp innerUserDaoImp;

    @Autowired
    public UserServiceImp(UserDaoImp userDaoImp) {
        innerUserDaoImp = userDaoImp;

        log.info("UserWebServiceImpl: Users created");
        User user1 = new User("Andre", "Matias", "andreMatias@gmail.com", "andreMatias", "123");
        User user2 = new User("Mari", "Lebron", "mariButterfly@gmail.com", "mariButterfly", "qwerty");
        User admin = new User("Super", "User", "admin@admin.com", "root", "admin");

        Role userRole = new Role("user");
        Role rootRole = new Role("admin");

        user1.setRoles(userRole);
        user2.setRoles(userRole);
        admin.setRoles(userRole);
        admin.setRoles(rootRole);

        log.info("UserWebServiceImpl: Roles was set");
        innerUserDaoImp.addUserToDatabase(user1);
        log.info("UserWebServiceImpl: user1 added");
        innerUserDaoImp.addUserToDatabase(user2);
        log.info("UserWebServiceImpl: user2 added");
        innerUserDaoImp.addUserToDatabase(admin);
        log.info("UserWebServiceImpl: admin added");
    }

    @Override
    public List<User> getUsersList() {
        log.info("UserWebServiceImpl: Users selected");
        return innerUserDaoImp.getUsersList();
    }

    @Transactional
    @Override
    public void addUserToDatabase(User formUser) {
        innerUserDaoImp.addUserToDatabase(formUser);
    }

    @Transactional
    @Override
    public void editUserData(User user) {
        log.info("userService start processing data");
        innerUserDaoImp.editUserData(user);
        log.info("userService finished processing data");
    }

    @Transactional
    @Override
    public void removeUserById(long id) throws IllegalArgumentException {
        innerUserDaoImp.removeUserById(id);
    }

}
