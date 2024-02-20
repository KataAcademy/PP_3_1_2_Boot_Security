package ru.kata.spring.boot_security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.models.Person;
import ru.kata.spring.boot_security.models.Role;
import ru.kata.spring.boot_security.repositories.PeopleRepository;
import ru.kata.spring.boot_security.repositories.RoleRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private final PeopleRepository peopleRepository;
    private final RoleRepository roleRepository;

    public AdminServiceImpl(PeopleRepository peopleRepository, RoleRepository roleRepository) {
        this.peopleRepository = peopleRepository;
        this.roleRepository = roleRepository;

    }


    public List<Person> getAllUsers() {
        return peopleRepository.findAll();
    }

    @Override
    public Person findUserByUserName(String firstName) {
        Optional<Person> user = peopleRepository.findByFirstName(firstName);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User " + firstName + " not found");
        return user.get();
    }

    @Override
    public void updateUser(Person person, List<String> roles) {
        System.out.println(roles);
        Person beforeUpdate = peopleRepository.getById(person.getId());
        person.setPassword(beforeUpdate.getPassword());
        Set<Role> roleSet = roles.stream()
                .map(roleRepository::findByStringId)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        person.setRoles(roleSet);
        peopleRepository.save(person);
    }

    @Override
    public void removeUser(Long id) {
        peopleRepository.delete(peopleRepository.getById(id));
    }

    public Person findOneById(Long id) {
        Optional<Person> user = peopleRepository.findById(id);
        if (user.isEmpty())
            throw new  UsernameNotFoundException("user не найден");
        return user.get();
    }
}
