package ru.kata.spring.boot_security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.models.Person;
import ru.kata.spring.boot_security.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private final PeopleRepository peopleRepository;

    public AdminServiceImpl(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
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
    public void updateUser(Person person) {
        Person beforeUpdate = peopleRepository.getById(person.getId());
        person.setPassword(beforeUpdate.getPassword());
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


//    // TODO: убрать в personService
//    public Optional<Person> findByUserName(String username) {
//        Optional<Person> user = peopleRepository.findByFirstName(username);
//        if (user.isEmpty())
//            throw new UsernameNotFoundException("User " + username + " not found");
//        return user;
//    }
}
