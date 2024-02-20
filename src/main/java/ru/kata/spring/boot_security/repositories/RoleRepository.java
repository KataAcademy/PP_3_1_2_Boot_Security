package ru.kata.spring.boot_security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.models.Role;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByNameOfRole(String nameOfRole);

    Optional<Role> findById(Long id);

    default Optional<Role> findByStringId(String stringId) {
        try {
            Long id = Long.parseLong(stringId);
            return findById(id);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
