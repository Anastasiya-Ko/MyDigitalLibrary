package ru.kopylova.springcourse.DigitalLibrary.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Person;

public interface BooksRepository extends JpaRepository<Book, Long>{

    Page<Book> findByPersonOwner(Person personOwner, Pageable pageable);
    Page<Book> findByAuthorOwner(Author authorOwner, Pageable pageable);
    Page<Book> findBooksByNameStartingWithOrderByAuthorOwner(String name, Pageable pageable);


}
