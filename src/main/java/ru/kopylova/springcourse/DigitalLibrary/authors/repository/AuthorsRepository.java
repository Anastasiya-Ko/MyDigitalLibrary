package ru.kopylova.springcourse.DigitalLibrary.authors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kopylova.springcourse.DigitalLibrary.authors.models.entity.Author;

import java.util.List;

public interface AuthorsRepository extends JpaRepository<Author, Long> {




    @Query(value = """
                SELECT DISTINCT a.id, a.name
                FROM author a LEFT JOIN book b ON b.author_id = a.id
                WHERE b.title IS NULL \s
                ORDER BY a.id""",
            nativeQuery = true
    )
    List<Author> findAuthorHasNoBooks();

}
