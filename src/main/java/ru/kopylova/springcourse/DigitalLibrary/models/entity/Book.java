package ru.kopylova.springcourse.DigitalLibrary.models.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity (name = "book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    //@JsonBackReference
    Person personOwner;

    @ManyToOne
    @JoinColumn(name = "author_id")
    //@JsonBackReference
    Author authorOwner;

    @Column(name = "name")
    String name;


    @Column(name = "year_of_publication")
    int yearOfPublication;

}
