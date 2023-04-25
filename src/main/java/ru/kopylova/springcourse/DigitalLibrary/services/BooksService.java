package ru.kopylova.springcourse.DigitalLibrary.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////Методы поиска//////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Page<BookDTO> readAllBooks(Pageable pageable) {
        Page<Book> entityPage = booksRepository.findAll(pageable);
        return entityPage.map(this::mapperToDTO);
    }

    public BookDTO readOneById(Long id) {
        return mapperToDTO(getById(id));
    }

    public Page<BookDTO> readBooksByNameStartingWith(String name, Pageable pageable) {

        String ex = String.format(("Книга с названием = %s не найдена"), name);

        Page<Book> entityList = booksRepository.findBooksByNameStartingWithOrderByAuthorOwner(name, pageable);

        if (entityList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityList.map(this::mapperToDTO);

    }

    public Page<BookDTO> readByPersonOwner(Person personOwner, Pageable pageable) {
        String ex = String.format(("У читателя = %s нет книг"), personOwner);
        return null;
    }

    /**
     * Метод внутреннего пользования для получения книги по её идентификатору
     */
    private Book getById(Long id) {
        String ex = String.format(("Книга с ID = %d не найдена"), id);
        return booksRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, ex));
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////Маппинг///////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
