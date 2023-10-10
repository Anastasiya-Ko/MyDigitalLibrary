package ru.kopylova.springcourse.DigitalLibrary.testReader.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

/***
 * Сущность Читатель
 */
@Entity(name = "test_reader")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestReader {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * Имя читателя
     */
    @Column(name = "name")
    String name;

    /**
     * Фамилия читателя
     */
    @Column(name = "last_name")
    String lastName;

    /**
     * Дата рождения читателя
     */
    @Column(name = "birthday")
    LocalDate birthday;

    /**
     * Электронная почта читателя
     */
    @Column(name = "email")
    String email;

}
