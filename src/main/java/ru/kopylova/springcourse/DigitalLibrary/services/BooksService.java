package ru.kopylova.springcourse.DigitalLibrary.services;

import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.kopylova.springcourse.DigitalLibrary.mappers.BookMapper;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Reader;
import ru.kopylova.springcourse.DigitalLibrary.models.view.BookDTOEasy;
import ru.kopylova.springcourse.DigitalLibrary.models.view.BookDTORich;
import ru.kopylova.springcourse.DigitalLibrary.repositories.BooksRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    @NonFinal
    @Lazy
    private AuthorService authorService;

    BookMapper bookMapper;

    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }


    public BookDTORich createBook(BookDTORich view) {
        Book entity = bookMapper.mapperToEntity(view, false);
        entity.setBookIsFree(true);
        booksRepository.save(entity);
        return bookMapper.mapperToDTORich(entity, true);
    }

    public BookDTORich updateBook(BookDTORich bookRequest, Long id) {

        Book entitySearch = getById(id);

        Book newEntity = bookMapper.mapperToEntity(bookRequest, false);
        newEntity.setId(entitySearch.getId());

        booksRepository.save(newEntity);

        return bookMapper.mapperToDTORich(newEntity, true);
    }

    public Page<BookDTOEasy> readAllBooks(Pageable pageable) {
        Page<Book> entityPage = booksRepository.findAll(pageable);
        return entityPage.map(entity -> bookMapper.mapperToDTOEasy(entity, true));
    }

    public BookDTORich readOneById(Long id) {
        return bookMapper.mapperToDTORich(getById(id), true);
    }

    public Page<BookDTORich> readBooksByTitleStartingWith(String title, Pageable pageable) {

        String ex = String.format(("Книга, начинающаяся на = %s не найдена"), title);

        Page<Book> entityPage = booksRepository.findBooksByTitleIgnoreCaseStartingWithOrderByAuthorOwner(title, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityPage.map(entity -> bookMapper.mapperToDTORich(entity, true));

    }

    public Page<BookDTORich> readBooksByReaderOwnerId(Reader readerOwner, Pageable pageable) {

        String ex = String.format(("У читателя с id = %s нет книг"), readerOwner.getId());

        Page<Book> entityPage = booksRepository.findBooksByReaderOwner(readerOwner, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityPage.map(entity -> bookMapper.mapperToDTORich(entity, true));
    }

    public Page<BookDTORich> readBooksByAuthorOwnerId(Author authorOwner, Pageable pageable) {
        String ex = String.format(("У читателя = %s нет книг"), authorOwner);

        authorService.readOneById(authorOwner.getId());
        Page<Book> entityPage = booksRepository.findBooksByAuthorOwner(authorOwner, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityPage.map(entity -> bookMapper.mapperToDTORich(entity, true));
    }

    public List<BookDTORich> readBooksIsFree() {

        List<Book> entityList = booksRepository.findBooksIsFree();

        return entityList.stream()
                .map(entity -> bookMapper.mapperToDTORich(entity, true))
                .collect(Collectors.toList());

    }


    public List<BookDTORich> readBooksIsNotFree() {

        List<Book> entityList = booksRepository.findAll().stream()
                .filter(book -> !(book.isBookIsFree()))
                .toList();

        return entityList.stream()
                .map(entity -> bookMapper.mapperToDTORich(entity, true))
                .collect(Collectors.toList());

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
        return booksRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, ex));
    }

}
