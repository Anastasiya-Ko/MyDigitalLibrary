package ru.kopylova.springcourse.DigitalLibrary.authors.models.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.kopylova.springcourse.DigitalLibrary.books.models.entity.Book;

import java.util.List;

/**
 * Сущность Автор для связи с БД
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "author")
public class Author {

    @Id
    @Column(name = "id")
    //подтверждает использование стратегии авто-инкремента id на стороне базы данных
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * Имя и Фамилия автора
     */
    @Column(name = "name")
    String name;

    /**
     * Книги автора
     */
    @ManyToMany(mappedBy = "authors")
    List<Book> books;

}
