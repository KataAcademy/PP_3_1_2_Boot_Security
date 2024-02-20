package ru.kata.spring.boot_security.until;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.models.Person;
import ru.kata.spring.boot_security.services.PeopleService;

/**
 * Валидатор для объекта Person.
 */
@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    /**
     * Конструктор валидатора.
     *
     * @param peopleService Сервис для работы с пользователями.
     */
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    /**
     * Поддерживает ли валидатор данную класс.
     *
     * @param clazz Класс для проверки.
     * @return true, если класс поддерживается, false в противном случае.
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    /**
     * Выполняет валидацию объекта Person.
     *
     * @param target Объект для валидации.
     * @param errors Объект для сохранения ошибок валидации.
     */
    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (peopleService.loadUserByUsername(person.getFirstName()).isPresent()
            && !peopleService.loadUserByUsername(person.getFirstName()).orElse(null).equals(target)) {
            errors.rejectValue("firstName", "", "A user with that name already exists");
        }
    }
}
