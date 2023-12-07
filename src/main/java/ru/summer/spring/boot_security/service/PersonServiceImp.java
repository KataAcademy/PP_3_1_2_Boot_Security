package ru.summer.spring.boot_security.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.summer.spring.boot_security.repository.PersonRepository;
import ru.summer.spring.boot_security.model.Person;
import ru.summer.spring.boot_security.model.Role;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImp implements PersonService, UserDetailsService {

    private final PersonRepository personRepository;
    private final RoleService roleService;


    @Autowired
    public PersonServiceImp(PersonRepository userRepository, RoleService roleService) {
        this.personRepository = userRepository;
        this.roleService = roleService;
    }

    @Transactional
    @Override
    public void add(Person user) {
        personRepository.save(user);
    }

    @Transactional
    @Override
    public void update(Person user) {
        personRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Person> getAllUsers() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username);
        if (person == null) {
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> authorities = person.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new User(person.getUsername(), person.getPassword(), authorities);
    }

    @Transactional(readOnly = true)
    @Override
    public String getRolesAsString(Person person) {
        return person.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(", "));
    }

    @Transactional
    @Override
    public void addRoleToPerson(String roleName, Person person) {
        Role role = roleService.findByName(roleName);
        if (role == null) {
            throw new RuntimeException("Role not found");
        }
        person.getRoles().add(role);
        personRepository.save(person);
    }
}
