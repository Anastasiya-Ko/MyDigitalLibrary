package ru.kopylova.springcourse.DigitalLibrary.readers.models.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Formula;
import ru.kopylova.springcourse.DigitalLibrary.books.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.dictionary.Gender;

import java.time.LocalDate;
import java.util.List;

/***
 * Сущность Читатель
 */
@Entity(name = "reader")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reader {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * Имя читателя
     */
    @Column(name = "first_name")
    String firstName;

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

    /**
     * Половая принадлежность
     */
    @Enumerated(EnumType.STRING)
    Gender gender;

    /**
     * Книга
     */
    @OneToMany(mappedBy = "readerOwner")
    List<Book> books;

    /**
     * Расчётное поле, для определения возраста читателя
     */
    @Formula("""
            (
            SELECT date_part('year',age(birthday ::date))
            FROM reader
            WHERE reader.id = id
            )
            """)
    Integer age;

}
