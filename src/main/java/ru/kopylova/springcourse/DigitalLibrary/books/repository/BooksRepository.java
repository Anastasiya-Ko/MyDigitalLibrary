package ru.kopylova.springcourse.DigitalLibrary.books.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kopylova.springcourse.DigitalLibrary.books.models.entity.Book;

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
                SELECT id, title, year_of_publication
                FROM book_author ba
                JOIN book b on ba.book_id = b.id
                GROUP BY id, title, year_of_publication
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
                SELECT id, title, year_of_publication
                FROM book_author ba
                JOIN book b on ba.book_id = b.id
                where ba.author_id = :authorId
        
                            """,
            nativeQuery = true
    )
    List<Book> findBooksWriteRequestAuthor(Long authorId);

    /**
     * Поиск свободных книг(находящихся в библиотеке на данный момент)
     */
    @Query(value = """
                SELECT id, title, year_of_publication
                FROM book_reader br
                RIGHT JOIN book b on br.book_id = b.id
                   """,
            nativeQuery = true
    )
    List<Book> findBooksFree();

    /**
     * Поиск книг, находящихся "на руках" у читателей
     */
    @Query(value = """
                SELECT DISTINCT id, title, year_of_publication
                FROM book_reader br\s
                LEFT JOIN book b on br.book_id = b.id
                   """,
            nativeQuery = true
    )
    List<Book> findBooksBusy();

    /**
     * Поиск книг, находящихся "на руках" у запрашиваемого читателя
     * @param readerId id читателя
     */
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
