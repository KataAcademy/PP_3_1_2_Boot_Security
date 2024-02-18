package ru.kata.spring.boot_security.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.models.Person;
import ru.kata.spring.boot_security.repositories.PeopleRepository;

@Service
public class RegistrationService {

    @Autowired
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {
       person.setPassword(passwordEncoder.encode(person.getPassword()));
       person.setRole("ROLE_USER");
       peopleRepository.save(person);
    }
}
