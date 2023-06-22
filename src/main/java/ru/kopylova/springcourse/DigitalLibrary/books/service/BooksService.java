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
import ru.kopylova.springcourse.DigitalLibrary.authors.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.authors.service.AuthorService;
import ru.kopylova.springcourse.DigitalLibrary.books.mapper.BookMapper;
import ru.kopylova.springcourse.DigitalLibrary.books.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTOEasy;
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTORich;
import ru.kopylova.springcourse.DigitalLibrary.books.repository.BooksRepository;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.entity.Reader;
import ru.kopylova.springcourse.DigitalLibrary.readers.service.ReadersService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BooksService {

    BooksRepository booksRepository;
    BookMapper bookMapper;
    AuthorService authorService;
    ReadersService readersService;
    JdbcTemplate jdbcTemplate;

    public BookDTOEasy createBook(BookDTOEasy view) {

        availableAuthors(view);
        Book entity = bookMapper.mapperToEntity(view, false);
        entity.setBookIsFree(true);
        booksRepository.save(entity);

        return bookMapper.mapperToDTOEasy(entity, true);

    }


    public BookDTOEasy updateBook(BookDTOEasy view) {

        Book updateEntity = getById(view.getId());
        availableAuthors(view);
        updateEntity = bookMapper.mapperToEntity(view, true);
        booksRepository.save(updateEntity);
        return bookMapper.mapperToDTOEasy(updateEntity, true);
    }

    /**
     * Метод для освобождения книги, при возвращении её в библиотеку
     * @param bookId возвращаемой книги
     */
    public String release(Long bookId) {
        Book entity = getById(bookId);
        if (!entity.isBookIsFree()) {
            jdbcTemplate.update("UPDATE book SET reader_id=NULL, is_free=true WHERE id=?", bookId);
            return String.format("Читатель %s вернул в библиотеку книгу \"%s\", автора(ов) %s",
                    entity.getReaderOwner().getLastName()
                            .concat(" ")
                            .concat(String.valueOf(entity.getReaderOwner().getFirstName().charAt(0)))
                            .concat("."),
                    entity.getTitle(), entity.getAuthors()
                            .stream().map(Author::getName)
                            .collect(Collectors.joining(", ")));
        } else return "Книга не может быть освобождена. Она хранится в библиотеке";
    }

    /**
     * Метод для назначения книги читателю
     * @param bookId   назначаемая книга
     * @param readerId читатель, берущий книгу
     * @return информационное сообщение
     */
    public String assignBookByReader(Long bookId, Long readerId) {
        Book entityBook = getById(bookId);
        if (entityBook.isBookIsFree()) {
            readersService.readOneById(readerId);
            jdbcTemplate.update("UPDATE book SET reader_id=?, is_free=false WHERE id=?", readerId, bookId);
            return String.format("Читатель %s взял книгу \"%s\", автора(ов) %s ",
                    readersService.readOneById(readerId).getLastName()
                            .concat(" ")
                            .concat(String.valueOf(readersService.readOneById(readerId).getFirstName().charAt(0)))
                            .concat("."),
                    entityBook.getTitle(),
                    entityBook.getAuthors()
                            .stream().map(Author::getName)
                            .collect(Collectors.joining(", ")));
        } else return "Книга не может быть выдана. Она занята другим читателем";

    }

    public Page<BookDTOEasy> readAllBooks(Pageable pageable) {
        Page<Book> entityPage = booksRepository.findAll(pageable);
        return entityPage.map(entity -> bookMapper.mapperToDTOEasy(entity, true));
    }

    public BookDTORich readOneById(Long id) {
        return bookMapper.mapperToDTORich(getById(id), true);
    }

    public Page<BookDTOEasy> readBooksByTitleStartingWith(String title, Pageable pageable) {

        Page<Book> entityPage = booksRepository.
                findBooksByTitleIgnoreCaseStartingWithOrderByYearOfPublicationAsc(title, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Книга, с названием начинающимся на '%s', не найдена", title));
        }

        return entityPage.map(entity -> bookMapper.mapperToDTOEasy(entity, true));

    }

    public Page<BookDTORich> readBooksByReaderOwnerId(Reader readerOwner, Pageable pageable) {

        String ex = String.format("Читатель %s не имеет книг из библиотеки", readerOwner.getLastName()
                .concat(" ")
                .concat(String.valueOf(readerOwner.getFirstName().charAt(0)))
                .concat("."));

        Page<Book> entityPage = booksRepository.findBooksByReaderOwner(readerOwner, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityPage.map(entity -> bookMapper.mapperToDTORich(entity, true));
    }

    public List<BookDTOEasy> readBooksFree() {

        List<Book> entityList = booksRepository.findBooksAreFree();

        return entityList.stream().map(entity -> bookMapper.mapperToDTOEasy(entity, true)).collect(Collectors.toList());

    }

    public List<BookDTORich> readBooksBusy() {

        List<Book> entityList = booksRepository.findAll().stream().filter(book -> !(book.isBookIsFree())).toList();

        return entityList.stream().map(entity -> bookMapper.mapperToDTORich(entity, true)).collect(Collectors.toList());

    }

    public List<BookDTOEasy> readBooksWriteGroupAuthors() {
        List<Book> entityList = booksRepository.findBooksWriteGroupAuthors();
        entityList.sort(Comparator.comparing(Book::getId));
        return entityList.stream().map(entity -> bookMapper.mapperToDTOEasy(entity, true)).collect(Collectors.toList());
    }

    public String deleteBookById(Long id) {
        Book entity = getById(id);
        booksRepository.deleteById(id);
        return String.format("Книга с названием \"%s\" успешно удалена", entity.getTitle());
    }


    /**
     * Метод внутреннего пользования для получения книги(сущности) по её идентификатору
     */
    private Book getById(Long id) {
        String ex = String.format(("Книга с номером = %d не найдена"), id);
        return booksRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ex));
    }


    /**
     * Метод внутреннего пользования для проверки наличия автора в бд
     */
    private void availableAuthors(BookDTOEasy view) {
        view.getAuthorsOwner().forEach(a -> authorService.readOneById(a.getId()));
    }


}
