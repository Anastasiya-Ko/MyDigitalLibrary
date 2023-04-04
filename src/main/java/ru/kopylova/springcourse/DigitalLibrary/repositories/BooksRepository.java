package ru.kopylova.springcourse.DigitalLibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Book;

public interface BooksRepository extends JpaRepository<Book, Long> {
}
