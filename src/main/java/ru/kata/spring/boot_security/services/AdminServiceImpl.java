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

/**
 * Реализация сервиса для администраторских операций с пользователями.
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final PeopleRepository peopleRepository;
    private final RoleRepository roleRepository;

    /**
     * Конструктор сервиса.
     *
     * @param peopleRepository Репозиторий для работы с пользователями.
     * @param roleRepository   Репозиторий для работы с ролями.
     */
    @Autowired
    public AdminServiceImpl(PeopleRepository peopleRepository, RoleRepository roleRepository) {
        this.peopleRepository = peopleRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Получает список всех пользователей.
     *
     * @return Список объектов Person.
     */
    @Override
    public List<Person> getAllUsers() {
        return peopleRepository.findAll();
    }

    /**
     * Находит пользователя по имени.
     *
     * @param firstName Имя пользователя.
     * @return Объект Person, представляющий пользователя.
     * @throws UsernameNotFoundException если пользователь не найден.
     */
    @Override
    public Person findUserByUserName(String firstName) {
        Optional<Person> user = peopleRepository.findByFirstName(firstName);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User " + firstName + " not found");
        return user.get();
    }

    /**
     * Обновляет информацию о пользователе и его ролях.
     *
     * @param person Обновленный объект Person.
     * @param roles  Список строковых идентификаторов ролей.
     */
    @Override
    public void updateUser(Person person, List<String> roles) {
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

    /**
     * Удаляет пользователя по ID.
     *
     * @param id ID пользователя для удаления.
     */
    @Override
    public void removeUser(Long id) {
        peopleRepository.delete(peopleRepository.getById(id));
    }

    /**
     * Находит пользователя по ID.
     *
     * @param id ID пользователя.
     * @return Объект Person, представляющий пользователя.
     * @throws UsernameNotFoundException если пользователь не найден.
     */
    @Override
    public Person findOneById(Long id) {
        Optional<Person> user = peopleRepository.findById(id);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return user.get();
    }
}
