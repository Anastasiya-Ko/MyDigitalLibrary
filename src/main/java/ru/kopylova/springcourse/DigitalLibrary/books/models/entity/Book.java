package ru.kopylova.springcourse.DigitalLibrary.books.models.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.kopylova.springcourse.DigitalLibrary.authors.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.entity.Reader;

import java.util.List;

/**
 * Сущность Книга
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * Читатель книги
     */
    @ManyToOne
    @JoinColumn(name = "reader_id")
    Reader readerOwner;

    /**
     * Автор(ы) книги
     */
    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    List<Author> authors;

    /**
     * Название книги
     */
    @Column(name = "title")
    String title;

    /**
     * Год публикации книги
     */
    @Column(name = "year_of_publication")
    Integer yearOfPublication;

}
