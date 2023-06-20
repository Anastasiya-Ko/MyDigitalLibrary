package ru.kopylova.springcourse.DigitalLibrary.books.models.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.kopylova.springcourse.DigitalLibrary.authors.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.entity.Reader;

import java.util.List;

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
    @JoinColumn(name = "reader_id")
    Reader readerOwner;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    List<Author> authors;

    @Column(name = "title")
    String title;


    @Column(name = "year_of_publication")
    Integer yearOfPublication;

    /**
     * Хранит статус книги: свободна - true, "на руках" - false
     */
    @Column(name = "is_free")
    boolean bookIsFree;
    
}
