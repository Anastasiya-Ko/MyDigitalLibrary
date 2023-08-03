package ru.kopylova.springcourse.DigitalLibrary.books.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.kopylova.springcourse.DigitalLibrary.authors.service.AuthorService;
import ru.kopylova.springcourse.DigitalLibrary.books.mapper.BookMapper;
import ru.kopylova.springcourse.DigitalLibrary.books.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTOEasy;
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTORich;
import ru.kopylova.springcourse.DigitalLibrary.books.repository.BooksRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с Книгами
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BooksService {

    BooksRepository booksRepository;
    BookMapper bookMapper;
    AuthorService authorService;
    JdbcTemplate jdbcTemplate;


    /**
     * Сохранение новой книги в бд
     *
     * @param view "облегчённое" представление книги, которую нужно сохранить
     * @return "облегчённое" представление книги, сохранённое в бд
     */
    public BookDTOEasy createBook(BookDTOEasy view) {

        //проверка на существование такого автора в бд
        availableAuthors(view);

        Book entity = bookMapper.mapperToEntity(view, false);
        booksRepository.save(entity);

        return bookMapper.mapperToDTOEasy(entity, true);
    }

    /**
     * Обновление существующей книги в бд
     *
     * @param view "облегчённое" представление книги, которую нужно обновить
     * @return "облегчённое" представление книги, обновлённое в бд
     */
    public BookDTOEasy updateBook(BookDTOEasy view) {

        //ищем в бд книгу с id, пришедшим в дто
        Book updateEntity = getById(view.getId());

        //проверка на существование такого автора в бд
        availableAuthors(view);

        updateEntity = bookMapper.mapperToEntity(view, true);
        booksRepository.save(updateEntity);

        return bookMapper.mapperToDTOEasy(updateEntity, true);
    }

    /**
     * Постраничный вывод справочника книг
     */
    public Page<BookDTOEasy> readAllBooks(Pageable pageable) {
        Page<Book> entityPage = booksRepository.findAll(pageable);
        return entityPage.map(entity -> bookMapper.mapperToDTOEasy(entity, true));
    }

    /**
     * Чтение полной информации о книге, по её id
     *
     * @param bookId идентификатор искомой книги
     */
    public BookDTORich readOneById(Long bookId) {
        return bookMapper.mapperToDTORich(getById(bookId), true);
    }

    /**
     * Чтение основной информации о книге, по началу её названия
     */
    public Page<BookDTOEasy> readBooksByTitleStartingWith(String title, Pageable pageable) {

        Page<Book> entityPage = booksRepository.
                findBooksByTitleIgnoreCaseStartingWithOrderByYearOfPublicationAsc(title, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Книга, с названием начинающимся на '%s', не найдена", title));
        }

        return entityPage.map(entity -> bookMapper.mapperToDTOEasy(entity, true));
    }

    /**
     * Чтение книг, написанных двумя и более авторами
     */
    public List<BookDTOEasy> readBooksWriteGroupAuthors() {
        List<Book> entityList = booksRepository.findBooksWriteGroupAuthors();
        entityList.sort(Comparator.comparing(Book::getTitle));
        return entityList.stream().map(entity -> bookMapper.mapperToDTOEasy(entity, true)).collect(Collectors.toList());
    }

    /**
     * Чтение книг, написанных запрашиваемым автором
     * @param authorId id искомого автора
     */
    public List<BookDTOEasy> readBooksWriteRequestAuthor(Long authorId) {

        //проверка на наличие искомого автора в бд
        var viewAuthor = authorService.readOneById(authorId);

        List<Book> entityList = booksRepository.findBooksWriteRequestAuthor(authorId);
        entityList.sort(Comparator.comparing(Book::getTitle));

        //Если в библиотеке нет книг, написанных данным автором
        if (entityList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("В библиотеке нет книг, которые написал %s", viewAuthor.getName()));
        }

        return entityList.stream().map(entity -> bookMapper.mapperToDTOEasy(entity, true)).collect(Collectors.toList());
    }

        /**
         * Удаление книги по её идентификатору
         * @param bookId идентификатор книги
         * @return Информационное сообщение
         */
    public String deleteBookById(Long bookId) {

        //поиск удаляемой книги в бд
        Book entity = getById(bookId);

        booksRepository.deleteById(bookId);
        return String.format("Книга с названием \"%s\" успешно удалена", entity.getTitle());
    }


    /**
     * Метод для получения книги(сущности) по её идентификатору
     */
    public Book getById(Long id) {
        String ex = String.format(("Книга с номером = %d не найдена"), id);
        return booksRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ex));
    }


    /**
     * Метод для проверки наличия автора в бд
     */
    private void availableAuthors(BookDTOEasy view) {
        view.getAuthorsOwner().forEach(a -> authorService.readOneById(a.getId()));
    }

}
