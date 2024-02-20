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

/**
 * Сервис, предоставляющий информацию о пользователях для Spring Security.
 */
@Service
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    /**
     * Конструктор сервиса.
     *
     * @param peopleRepository Репозиторий для работы с пользователями.
     */
    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    /**
     * Загружает пользователя по его имени для Spring Security.
     *
     * @param username Имя пользователя.
     * @return Объект UserDetails, представляющий информацию о пользователе.
     * @throws UsernameNotFoundException если пользователь не найден.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> personOptional = peopleRepository.findByFirstName(username);

        if (personOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }

        return new PersonDetails(personOptional.get());
    }
}
