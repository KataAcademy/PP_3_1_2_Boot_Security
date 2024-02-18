package ru.kata.spring.boot_security.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Table(name = "Preson", schema = "web_admin_security")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "Имя не должно быть пустым")
    private String firstName;


    @Column(name = "last_name")
    @NotEmpty(message = "Имя не должно быть пустым")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Имя не должно быть пустым")
    private String password;

    public Person() {
    }

    public Person(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Persson " +
               "id = " + id +
               ", firstName = '" + firstName + '\'' +
               ", lastName = '" + lastName + '\'' +
               ", email = '" + email + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person persson = (Person) o;
        return Objects.equals(getId(), persson.getId()) && Objects.equals(getFirstName(), persson.getFirstName()) && Objects.equals(getLastName(), persson.getLastName()) && Objects.equals(getEmail(), persson.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getEmail());
    }
}
