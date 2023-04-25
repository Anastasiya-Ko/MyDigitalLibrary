package ru.kopylova.springcourse.DigitalLibrary.models.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Formula;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "person")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "birthday")
    LocalDate birthday;

    @Column(name = "email")
    String email;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @JsonIgnore
    @OneToMany(mappedBy = "personOwner")
    List<Book> books;

    @Formula("""
            (
            SELECT date_part('year',age(birthday ::date))
            FROM person
            WHERE person.id = id
            )
            """)
    Integer age;

}
