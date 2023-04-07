package ru.kopylova.springcourse.DigitalLibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Person;

import java.util.List;

public interface AuthorsRepository extends JpaRepository<Author, Long> {

}
