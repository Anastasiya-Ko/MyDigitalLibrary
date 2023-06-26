package ru.kopylova.springcourse.DigitalLibrary.books.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kopylova.springcourse.DigitalLibrary.books.models.entity.Book;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book, Long>{
    Page<Book> findBooksByTitleIgnoreCaseStartingWithOrderByYearOfPublicationAsc(String title, Pageable pageable);

    @Query(value = """
                SELECT id, title, year_of_publication
                FROM book_author ba
                JOIN book b on ba.book_id = b.id
                GROUP BY id, title, year_of_publication
                HAVING COUNT(author_id) >= 2
                   """,
            nativeQuery = true
    )
    List<Book> findBooksWriteGroupAuthors();

    @Query(value = """
                SELECT id, title, year_of_publication
                FROM book_author ba
                JOIN book b on ba.book_id = b.id
                where ba.author_id = :id
        
                            """,
            nativeQuery = true
    )
    List<Book> findBooksWriteRequestAuthor(Long id);

    @Query(value = """
                SELECT id, title, year_of_publication
                FROM book_reader br
                RIGHT JOIN book b on br.book_id = b.id
                   """,
            nativeQuery = true
    )
    List<Book> findBooksFree();

    @Query(value = """
                SELECT DISTINCT id, title, year_of_publication
                FROM book_reader br\s
                LEFT JOIN book b on br.book_id = b.id
                   """,
            nativeQuery = true
    )
    List<Book> findBooksBusy();

    @Query(value = """
                SELECT b.id, title, year_of_publication
                FROM book b
                JOIN book_reader br on b.id = br.book_id\s
                RIGHT JOIN reader r on br.reader_id = r.id
                WHERE r.id = :readerId
                   """,
            nativeQuery = true
    )
    List<Book> findBooksByReaderOwner(Long readerId);
}
