package ru.kopylova.springcourse.DigitalLibrary.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Reader;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book, Long>{


    Page<Book> findBooksByReaderOwner(Reader readerOwnerId, Pageable pageable);
    Page<Book> findBooksByAuthorOwner(Author authorOwner, Pageable pageable);
    Page<Book> findBooksByTitleIgnoreCaseStartingWithOrderByAuthorOwner(String title, Pageable pageable);

    @Query(value = """
                SELECT *
                FROM book b
                WHERE is_free = true
                            """,
            nativeQuery = true
        )
    List<Book> findBooksAreFree();

}
