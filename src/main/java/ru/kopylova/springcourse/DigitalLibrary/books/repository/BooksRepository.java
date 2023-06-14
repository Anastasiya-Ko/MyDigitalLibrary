package ru.kopylova.springcourse.DigitalLibrary.books.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kopylova.springcourse.DigitalLibrary.books.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.entity.Reader;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book, Long>{


    Page<Book> findBooksByReaderOwner(Reader readerOwnerId, Pageable pageable);
    Page<Book> findBooksByTitleIgnoreCaseStartingWithOrderByYearOfPublicationAsc(String title, Pageable pageable);

    @Query(value = """
                SELECT *
                FROM book b
                WHERE is_free = true
                            """,
            nativeQuery = true
        )
    List<Book> findBooksAreFree();

    @Query(value = """
                SELECT id, reader_id, title, year_of_publication, is_free
                FROM book_author ba
                JOIN book b on ba.book_id = b.id
                GROUP BY id, reader_id, title, year_of_publication, is_free
                HAVING COUNT(author_id) >= 2
        
                            """,
            nativeQuery = true
    )
    List<Book> findBooksWriteGroupAuthors();

}
