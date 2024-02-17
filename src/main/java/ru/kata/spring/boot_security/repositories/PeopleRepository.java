package ru.kata.spring.boot_security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.models.Persson;

import java.util.Optional;

public interface PeopleRepository extends JpaRepository<Persson, Long> {
    Optional<Persson> findByFirstName (String firstName);

}
