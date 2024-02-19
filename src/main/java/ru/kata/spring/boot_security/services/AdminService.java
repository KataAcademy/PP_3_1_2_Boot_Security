package ru.kata.spring.boot_security.services;

import ru.kata.spring.boot_security.models.Person;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<Person> getAllUsers();

    Person findUserByUserName(String firstName);

    //    void addUser(Person person);
    void updateUser(Person person);

    void removeUser(Long id);

    Person findOneById(Long id);
}
