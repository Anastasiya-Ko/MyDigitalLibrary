package ru.kopylova.springcourse.DigitalLibrary.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Person;
import ru.kopylova.springcourse.DigitalLibrary.models.view.BookDTO;
import ru.kopylova.springcourse.DigitalLibrary.repositories.BooksRepository;

@Service
public class BooksService {

    private final BooksRepository booksRepository;

    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }


    public BookDTO createBook(BookDTO view) {
        Book entity = mapperToEntity(view);
        booksRepository.save(entity);
        return mapperToDTO(entity);
    }

    public BookDTO updateBook(BookDTO bookRequest, Long id) {
        Book entity = getById(id);

        Book newEntity = mapperToEntity(bookRequest);
        newEntity.setId(entity.getId());

        booksRepository.save(newEntity);

        return mapperToDTO(newEntity);
    }

    public Page<BookDTO> readAllBooks(Pageable pageable) {
        Page<Book> entityPage = booksRepository.findAll(pageable);
        return entityPage.map(this::mapperToDTO);
    }

    public BookDTO readOneById(Long id) {
        return mapperToDTO(getById(id));
    }

    public Page<BookDTO> readBooksByNameStartingWith(String name, Pageable pageable) {

        String ex = String.format(("Книга, начинающаяся на = %s не найдена"), name);

        Page<Book> entityPage = booksRepository.findBooksByNameIgnoreCaseStartingWithOrderByAuthorOwner(name, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityPage.map(this::mapperToDTO);

    }

    public Page<BookDTO> readBooksByPersonOwner(Person personOwner, Pageable pageable) {
        String ex = String.format(("У читателя = %s нет книг"), personOwner);

        Page<Book> entityPage = booksRepository.findBooksByPersonOwner(personOwner, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityPage.map(this::mapperToDTO);
    }

    public Page<BookDTO> readBooksByAuthorOwner(Author authorOwner, Pageable pageable) {
        String ex = String.format(("У читателя = %s нет книг"), authorOwner);

        Page<Book> entityPage = booksRepository.findBooksByAuthorOwner(authorOwner, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityPage.map(this::mapperToDTO);
    }

    public String deleteBookById(Long id) {
        getById(id);

        booksRepository.deleteById(id);

        return String.format("Книга с id=%d успешно удалена", id);
    }


    /**
     * Метод внутреннего пользования для получения книги по её идентификатору
     */
    private Book getById(Long id) {
        String ex = String.format(("Книга с ID = %d не найдена"), id);
        return booksRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, ex));
    }

    private Book mapperToEntity(BookDTO view) {
        Book entity = new Book();

        entity.setName(view.getName());
        entity.setYearOfPublication(view.getYearOfPublication());
        entity.setAuthorOwner(view.getAuthorOwner());
        entity.setPersonOwner(view.getPersonOwner());

        return entity;

    }

    private BookDTO mapperToDTO(Book entity) {

        BookDTO view = new BookDTO();

        view.setName(entity.getName());
        view.setYearOfPublication(entity.getYearOfPublication());
        view.setAuthorOwner(entity.getAuthorOwner());
        view.setPersonOwner(entity.getPersonOwner());

        return view;
    }
}
