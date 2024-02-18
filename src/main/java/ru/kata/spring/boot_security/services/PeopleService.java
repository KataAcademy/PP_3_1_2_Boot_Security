package ru.kata.spring.boot_security.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.models.Person;
import ru.kata.spring.boot_security.repositories.PeopleRepository;

import java.util.Optional;

@Service
public class PeopleService {


    // TODO проверить ошибки тут
    private final PeopleRepository peopleRepository;

    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public Optional<Person> loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> persson = peopleRepository.findByFirstName(username);

        if(persson.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }

        return persson;
    }
}
