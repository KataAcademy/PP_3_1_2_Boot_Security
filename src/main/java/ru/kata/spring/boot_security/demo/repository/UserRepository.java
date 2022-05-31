package ru.kata.spring.boot_security.demo.repository;


import org.springframework.data.repository.CrudRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer>
{
    Optional<User> findByEmail(String email);
}
