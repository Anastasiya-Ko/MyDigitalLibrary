package ru.kopylova.springcourse.DigitalLibrary.models.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "person")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "birthday")
    @JsonFormat(pattern="dd-MM-yyyy")
    LocalDate birthday;

    @Column(name = "email")
    String email;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @JsonIgnore
    //@JsonManagedReference
    @OneToMany(mappedBy = "owner")
    List<Book> books;


}
