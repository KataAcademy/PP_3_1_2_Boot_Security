package ru.summer.spring.boot_security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.summer.spring.boot_security.model.Role;
import ru.summer.spring.boot_security.repository.RoleRepository;

/**
 * Имплементация сервиса для работы с ролями (Role).
 * Реализует интерфейс RoleService.
 */
@Service
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    /**
     * Конструктор, в который внедряется зависимость - репозиторий ролей.
     *
     * @param roleRepository Репозиторий для работы с ролями.
     */
    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
