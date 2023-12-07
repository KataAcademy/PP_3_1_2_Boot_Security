package ru.summer.spring.boot_security.service;

import ru.summer.spring.boot_security.model.Person;

import java.util.List;

public interface PersonService {

    void add(Person user);

    void update(Person user);

    void delete(Long id);

    List<Person> getAllUsers();

    String getRolesAsString(Person person);

    void addRoleToPerson(String roleName, Person person);

}
