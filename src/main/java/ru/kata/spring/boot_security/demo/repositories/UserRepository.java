package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "User.roles")
    Optional<User> findByUsername(String username);
    @EntityGraph(value = "User.roles")
    Optional<User> findById(Long id);
    @Query("select distinct u from User u left join fetch u.roles")
    List<User> findUsers();

    void deleteUserById(Long id);

}
