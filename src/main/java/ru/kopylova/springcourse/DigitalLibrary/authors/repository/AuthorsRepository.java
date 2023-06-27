package ru.kopylova.springcourse.DigitalLibrary.authors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kopylova.springcourse.DigitalLibrary.authors.models.entity.Author;

import java.util.List;

/**
 * Репозиторий для связи с таблицей Автор в базе данных
 */
public interface AuthorsRepository extends JpaRepository<Author, Long> {

    /**
     * Метод для поиска авторов, книги которых не представлены в библиотеке
     * @return сущность автора
     */
    @Query(value = """
                SELECT DISTINCT a.id, a.name
                FROM author a LEFT JOIN book_author ba ON ba.author_id = a.id
                WHERE ba.book_id IS NULL \s
                ORDER BY a.id""",
            nativeQuery = true
    )
    List<Author> findAuthorHasNoBooks();

}
