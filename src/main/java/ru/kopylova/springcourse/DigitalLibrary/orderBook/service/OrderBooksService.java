package ru.kopylova.springcourse.DigitalLibrary.orderBook.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.kopylova.springcourse.DigitalLibrary.books.mapper.BookMapper;
import ru.kopylova.springcourse.DigitalLibrary.books.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTOEasy;
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTORich;
import ru.kopylova.springcourse.DigitalLibrary.books.repository.BooksRepository;
import ru.kopylova.springcourse.DigitalLibrary.books.service.BooksService;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.entity.Reader;
import ru.kopylova.springcourse.DigitalLibrary.readers.service.ReadersService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с оборотом книг в библиотеке
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderBooksService {

    BooksRepository booksRepository;
    BooksService booksService;
    BookMapper bookMapper;
    ReadersService readersService;

    /**
     * Метод для освобождения книги, при возвращении её в библиотеку
     *
     * @param bookId возвращаемой книги
     */
    public String release(Long bookId) {

        var entity = booksService.getById(bookId);

        if (entity.getReaderOwner() != null) {
            entity.setReaderOwner(null);
            booksRepository.save(entity);
            return "Читатель вернул книгу в библиотеку";
        } else return "Книга не может быть освобождена. Она хранится в библиотеке";
    }


    /**
     * Метод для назначения книги читателю
     *
     * @param bookId   назначаемая книга
     * @param readerId читатель, берущий книгу
     * @return информационное сообщение
     */
    public String assignBookByReader(Long bookId, Long readerId) {

        Book entityBook = booksService.getById(bookId);
        Reader entityReader = readersService.getById(readerId);

        entityBook.setReaderOwner(entityReader);
        booksRepository.save(entityBook);

        return "Читатель взял книгу";

    }


    /**
     * Чтение свободных книг, находящихся в библиотеке
     */
    public List<BookDTOEasy> readBooksFree() {

        List<Book> entityList = booksRepository.findBooksFree();
        entityList.sort(Comparator.comparing(Book::getTitle));

        return entityList.stream().map(entity -> bookMapper.mapperToDTOEasy(entity, true)).collect(Collectors.toList());
    }

    /**
     * Чтение книг, находящихся "на руках" у читателей
     */
    public List<BookDTORich> readBooksBusy() {

        List<Book> entityList = booksRepository.findBooksBusy();
        entityList.sort(Comparator.comparing(Book::getTitle));
        return entityList.stream().map(entity -> bookMapper.mapperToDTORich(entity, true)).collect(Collectors.toList());

    }

    /**
     * Чтение книг, находящихся "на руках" у конкретного читателя
     *
     * @param readerId идентификатор искомого читателя
     */
    public List<BookDTOEasy> readBooksByReaderOwnerId(Long readerId) {

        Reader entityReader = readersService.getById(readerId);

        List<Book> entityList = booksRepository.findBooksByReaderOwner(entityReader);

        //Если у читателя нет книг "на руках"
        if (entityList.get(0) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "У читателя нет взятых книг");
        }

        return entityList.stream().map(entity -> bookMapper.mapperToDTOEasy(entity, true)).collect(Collectors.toList());
    }

}
