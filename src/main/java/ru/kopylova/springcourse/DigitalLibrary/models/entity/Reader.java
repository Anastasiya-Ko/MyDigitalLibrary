package ru.kopylova.springcourse.DigitalLibrary.models.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Formula;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "reader")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reader {

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
    @OneToMany(mappedBy = "readerOwner")
    List<Book> books;

    @Formula("""
            (
            SELECT date_part('year',age(birthday ::date))
            FROM reader
            WHERE reader.id = id
            )
            """)
    Integer age;

}
