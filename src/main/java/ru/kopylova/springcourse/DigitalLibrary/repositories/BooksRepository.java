package ru.kopylova.springcourse.DigitalLibrary.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Person;

public interface BooksRepository extends JpaRepository<Book, Long>{

    Page<Book> findBooksByPersonOwner(Person personOwnerLastName, Pageable pageable);
    Page<Book> findBooksByAuthorOwner(Author authorOwner, Pageable pageable);
    Page<Book> findBooksByNameIgnoreCaseStartingWithOrderByAuthorOwner(String name, Pageable pageable);


}
