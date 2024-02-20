package ru.kata.spring.boot_security.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.models.Person;
import ru.kata.spring.boot_security.models.Role;
import ru.kata.spring.boot_security.repositories.PeopleRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Сервис для работы с пользователями.
 */
@Service
@Transactional
public class PeopleService {

    private final PeopleRepository peopleRepository;

    /**
     * Конструктор сервиса.
     *
     * @param peopleRepository Репозиторий для работы с пользователями.
     */
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    /**
     * Загружает пользователя по имени пользователя.
     *
     * @param username Имя пользователя.
     * @return Объект Optional, содержащий информацию о пользователе, если найден.
     * @throws UsernameNotFoundException если пользователь не найден.
     */
    public Optional<Person> loadUserByUsername(String username) throws UsernameNotFoundException {
        return peopleRepository.findByFirstName(username);
    }

    /**
     * Получает роли пользователя по его имени.
     *
     * @param username Имя пользователя.
     * @return Множество ролей пользователя.
     */
    public Set<Role> getUserRoles(String username) {
        Optional<Person> userOptional = peopleRepository.findByFirstName(username);
        return userOptional.map(Person::getRoles).orElse(Collections.emptySet());
    }
}
