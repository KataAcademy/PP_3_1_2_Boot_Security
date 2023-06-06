package ru.kata.spring.boot_security.demo.model;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "users")
@Valid
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @Column
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    @NotEmpty(message = "lastName should not be empty")
    private  String lastName;

    public User(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
