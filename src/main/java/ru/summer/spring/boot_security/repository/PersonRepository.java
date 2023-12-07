package ru.summer.spring.boot_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.summer.spring.boot_security.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByUsername(String username);

}
