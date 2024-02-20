package ru.kata.spring.boot_security.services;

import ru.kata.spring.boot_security.models.Person;

import java.util.List;

public interface AdminService {
    List<Person> getAllUsers();

    Person findUserByUserName(String firstName);

    void updateUser(Person person, List<String> roles);

    void removeUser(Long id);

    Person findOneById(Long id);
}
