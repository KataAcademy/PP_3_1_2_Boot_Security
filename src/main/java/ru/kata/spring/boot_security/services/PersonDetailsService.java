package ru.kata.spring.boot_security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.models.Person;
import ru.kata.spring.boot_security.repositories.PeopleRepository;
import ru.kata.spring.boot_security.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    @Autowired
    private final PeopleRepository peopleRepository;

    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> persson = peopleRepository.findByFirstName(username);

        if(persson.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }

        return new PersonDetails(persson.get());
    }
}
