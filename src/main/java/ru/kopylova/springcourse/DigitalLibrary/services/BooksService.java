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

    private final AuthorService authorService;

    public BooksService(BooksRepository booksRepository, AuthorService authorService) {
        this.booksRepository = booksRepository;
        this.authorService = authorService;
    }

    public BookDTO createBook(BookDTO view) {
        Book entity = mapperToEntity(view, false);
        booksRepository.save(entity);
        return mapperToDTO(entity, true);
    }

    public BookDTO updateBook(BookDTO bookRequest) {

        Book newEntity = mapperToEntity(bookRequest, false);

        booksRepository.save(newEntity);

        return mapperToDTO(newEntity, true);
    }

    public Page<BookDTO> readAllBooks(Pageable pageable) {
        Page<Book> entityPage = booksRepository.findAll(pageable);
        return entityPage.map(entity -> mapperToDTO(entity, true));
    }

    public BookDTO readOneById(Long id) {
        return mapperToDTO(getById(id), true);
    }

    public Page<BookDTO> readBooksByNameStartingWith(String name, Pageable pageable) {

        String ex = String.format(("Книга, начинающаяся на = %s не найдена"), name);

        Page<Book> entityPage = booksRepository.findBooksByNameIgnoreCaseStartingWithOrderByAuthorOwner(name, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityPage.map(entity -> mapperToDTO(entity, true));

    }


    public Page<BookDTO> readBooksByPersonOwnerId(Person personOwner, Pageable pageable) {

        String ex = String.format(("У читателя с id = %s нет книг"), personOwner.getId());

        Page<Book> entityPage = booksRepository.findBooksByPersonOwner(personOwner, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityPage.map(entity -> mapperToDTO(entity, true));
    }

    public Page<BookDTO> readBooksByAuthorOwner(Author authorOwner, Pageable pageable) {
        String ex = String.format(("У читателя = %s нет книг"), authorOwner);

        Page<Book> entityPage = booksRepository.findBooksByAuthorOwner(authorOwner, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityPage.map(entity -> mapperToDTO(entity, true));
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

    public Book mapperToEntity(BookDTO view, boolean isWrite) {
        Book entity = new Book();

        if(isWrite) {
            entity.setId(view.getId());
        }
        if (view.getAuthorOwner() != null) {
            authorService.readOneById(view.getAuthorOwner().getId());
            entity.setAuthorOwner(authorService.mapperToEntity(view.getAuthorOwner(), true));
        }
        entity.setName(view.getName());
        entity.setYearOfPublication(view.getYearOfPublication());

        return entity;

    }

    public BookDTO mapperToDTO(Book entity, boolean isWrite) {

        BookDTO view = new BookDTO();
        if(isWrite) {
            view.setId(entity.getId());
        }
        if(entity.getAuthorOwner() != null){
            authorService.readOneById(entity.getAuthorOwner().getId());
            view.setAuthorOwner(authorService.mapperToDTO(entity.getAuthorOwner(), true));
        }

        view.setName(entity.getName());
        view.setYearOfPublication(entity.getYearOfPublication());

        return view;
    }
}
