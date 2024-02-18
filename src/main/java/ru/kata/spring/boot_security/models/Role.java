//package ru.kata.spring.boot_security.models;
//
//
//import org.springframework.security.core.GrantedAuthority;
//
//import javax.persistence.*;
//import java.util.Collection;
//import java.util.Objects;
//import java.util.Set;
//
//@Entity
//public class Role implements GrantedAuthority {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "name_of_role")
//    private String name_of_role;
//
//    @ManyToMany(mappedBy = "roles")
//    private Collection<Person> people;
//
//    public Role(){
//
//    }
//
//    public Role(String name_of_role) {
//        this.name_of_role = name_of_role;
//    }
//
//    public Role(Long id, String name_of_role, Collection<Person> people) {
//        this.id = id;
//        this.name_of_role = name_of_role;
//        this.people = people;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName_of_role() {
//        return name_of_role;
//    }
//
//    public void setName_of_role(String name_of_role) {
//        this.name_of_role = name_of_role;
//    }
//
//    public Collection<Person> getPeople() {
//        return people;
//    }
//
//    public void setPeople(Collection<Person> people) {
//        this.people = people;
//    }
//
//
//    @Override
//    public String getAuthority() {
//        return getName_of_role();
//    }
//
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Role role = (Role) o;
//        return Objects.equals(getId(), role.getId()) && Objects.equals(getName_of_role(), role.getName_of_role()) && Objects.equals(getPeople(), role.getPeople());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId(), getName_of_role(), getPeople());
//    }
//}
