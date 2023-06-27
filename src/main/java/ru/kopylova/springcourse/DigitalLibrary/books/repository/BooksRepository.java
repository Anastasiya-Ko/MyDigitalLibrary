package ru.kopylova.springcourse.DigitalLibrary.books.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kopylova.springcourse.DigitalLibrary.books.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.entity.Reader;

import java.util.List;

/**
 * Репозиторий для взаимодействия с таблицей Книги в базе данных
 */
public interface BooksRepository extends JpaRepository<Book, Long>{
    /**
     * Поиск книг по началу названия, с игнорированием кейса и сортировкой по возрастанию года публикации
     * @param title начало названия книги
     */
    Page<Book> findBooksByTitleIgnoreCaseStartingWithOrderByYearOfPublicationAsc(String title, Pageable pageable);

    /**
     * Поиск книг в бд, написанных группой авторов
     */
    @Query(value = """
                SELECT id, title, year_of_publication, reader_id
                FROM book_author ba
                JOIN book b on ba.book_id = b.id
                GROUP BY id, title, year_of_publication, reader_id
                HAVING COUNT(author_id) >= 2
                   """,
            nativeQuery = true
    )
    List<Book> findBooksWriteGroupAuthors();

    /**
     * Поиск в бд книг, написанных запрашиваемым автором
     * @param authorId идентификатор автора
     */
    @Query(value = """
                SELECT *
                FROM book_author ba
                JOIN book b on ba.book_id = b.id
                WHERE ba.author_id = :authorId
        
                            """,
            nativeQuery = true
    )
    List<Book> findBooksWriteRequestAuthor(Long authorId);

    /**
     * Поиск свободных книг(находящихся в библиотеке на данный момент)
     */
    @Query(value = """
                SELECT *
                FROM book b
                WHERE reader_id IS NULL
                   """,
            nativeQuery = true
    )
    List<Book> findBooksFree();

    /**
     * Поиск книг, находящихся "на руках" у читателей
     */
    @Query(value = """
                SELECT *
                FROM book
                WHERE reader_id IS NOT NULL
                   """,
            nativeQuery = true
    )
    List<Book> findBooksBusy();

    /**
     * Поиск книг, находящихся "на руках" у запрашиваемого читателя
     */
    List<Book> findBooksByReaderOwner(Reader reader);

}
