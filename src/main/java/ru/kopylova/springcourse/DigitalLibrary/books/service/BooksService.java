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
import ru.kopylova.springcourse.DigitalLibrary.readers.models.entity.Reader;
import ru.kopylova.springcourse.DigitalLibrary.readers.service.ReadersService;

import java.util.List;
import java.util.stream.Collectors;

//TODO установить плагин для проверки кода
//TODO дореализовывать метод пакетного обновления
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

        Book entity = new Book();
        if (!view.getAuthorsOwner().isEmpty()) {
            view.getAuthorsOwner()
                    .stream()
                    .map(a -> authorService.readOneById(a.getId()))
                    .toList();
            entity = bookMapper.mapperToEntity(view, false);
            booksRepository.save(entity);

        }
        return bookMapper.mapperToDTOEasy(entity, true);
    }

//    public String createBooksWithBathUpdate() {
//        List<Book> books = new ArrayList<>();
//        jdbcTemplate.batchUpdate("INSERT INTO book VALUES (?,?,?,?,?,?)",
//                new BatchPreparedStatementSetter() {
//
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//                ps.setLong(1, books.get(i).getId());
//                ps.setObject(2, books.get(i).getReaderOwner());
//                ps.setString(3, books.get(i).getTitle());
//                ps.setObject(4, books.get(i).getAuthorOwner());
//                ps.setDate(5, Date.valueOf(books.get(i).getYearOfPublication()));
//                ps.setBoolean(6, books.get(i).isBookIsFree());
//            }
//
//            @Override
//            public int getBatchSize() {
//                return books.size();
//            }
//        });
//        return "Книги успешно добавлены";
//    }

    public BookDTOEasy updateBook(BookDTOEasy view) {

        Book updateEntity = getById(view.getId());
        if (!view.getAuthorsOwner().isEmpty()) {
            view.getAuthorsOwner()
                    .stream()
                    .map(a -> authorService.readOneById(a.getId()))
                    .toList();
            updateEntity = bookMapper.mapperToEntity(view, true);
            booksRepository.save(updateEntity);
        }

        return bookMapper.mapperToDTOEasy(updateEntity, true);
    }

    /**
     * Метод для освобождения книги, при возвращении её в библиотеку
     *
     * @param bookId возвращаемой книги
     */
    public String release(Long bookId) {
        jdbcTemplate.update("UPDATE book SET reader_id=NULL, is_free=true WHERE id=?", bookId);
        return "Теперь книга свободна";
    }

    /**
     * Метод для назначения книги читателю
     * @param bookId назначаемая книга
     * @param readerId читатель, берущий книгу
     * @return информационное сообщение
     */
    public String assignBookByReader(Long bookId, Long readerId) {
        getById(bookId);
        readersService.readOneById(readerId);
        jdbcTemplate.update("UPDATE book SET reader_id=?, is_free=false WHERE id=?", readerId, bookId);
        return String.format("Книга с id=%d выдана читателю с фамилией %s", bookId,
                readersService.readOneById(readerId).getLastName());

    }


    public Page<BookDTOEasy> readAllBooks(Pageable pageable) {
        Page<Book> entityPage = booksRepository.findAll(pageable);
        return entityPage.map(entity -> bookMapper.mapperToDTOEasy(entity, true));
    }

    public BookDTORich readOneById(Long id) {
        return bookMapper.mapperToDTORich(getById(id), true);
    }

    public Page<BookDTOEasy> readBooksByTitleStartingWith(String title, Pageable pageable) {

        String ex = String.format(("Книга, начинающаяся на = %s не найдена"), title);

        Page<Book> entityPage = booksRepository.
                findBooksByTitleIgnoreCaseStartingWithOrderByYearOfPublicationAsc(title, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityPage.map(entity -> bookMapper.mapperToDTOEasy(entity, true));

    }

    public Page<BookDTORich> readBooksByReaderOwnerId(Reader readerOwner, Pageable pageable) {

        String ex = String.format(("У читателя с id = %s нет книг"), readerOwner.getId());

        Page<Book> entityPage = booksRepository.findBooksByReaderOwner(readerOwner, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityPage.map(entity -> bookMapper.mapperToDTORich(entity, true));
    }

    public List<BookDTOEasy> readBooksAreFree() {

        List<Book> entityList = booksRepository.findBooksAreFree();

        return entityList.stream().map(entity -> bookMapper.mapperToDTOEasy(entity, true)).collect(Collectors.toList());

    }

    public List<BookDTORich> readBooksAreNotFree() {

        List<Book> entityList = booksRepository.findAll().stream().filter(book -> !(book.isBookIsFree())).toList();

        return entityList.stream().map(entity -> bookMapper.mapperToDTORich(entity, true)).collect(Collectors.toList());

    }

    public String deleteBookById(Long id) {
        getById(id);

        booksRepository.deleteById(id);

        return String.format("Книга с id=%d успешно удалена", id);
    }


    /**
     * Метод внутреннего пользования для получения книги(сущности) по её идентификатору
     */
    private Book getById(Long id) {
        String ex = String.format(("Книга с ID = %d не найдена"), id);
        return booksRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ex));
    }

}
