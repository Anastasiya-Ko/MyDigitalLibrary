package ru.kopylova.springcourse.DigitalLibrary.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Person;
import ru.kopylova.springcourse.DigitalLibrary.models.view.BookDTOEasy;
import ru.kopylova.springcourse.DigitalLibrary.models.view.BookDTORich;
import ru.kopylova.springcourse.DigitalLibrary.repositories.BooksRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  //создает конструктор ТОЛЬКО с опред аргументами - private final
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BooksService {


    BooksRepository booksRepository;

    AuthorService authorService;

    PeopleService peopleService;


    public BookDTORich createBook(BookDTORich view) {
        Book entity = mapperToEntityRich(view, false);
        entity.setBookIsFree(true);
        booksRepository.save(entity);
        return mapperToDTORich(entity, true);
    }

    public BookDTORich updateBook(BookDTORich bookRequest, Long id) {

        Book entitySearch = getById(id);

        Book newEntity = mapperToEntityRich(bookRequest, false);
        newEntity.setId(entitySearch.getId());

        booksRepository.save(newEntity);

        return mapperToDTORich(newEntity, true);
    }

    public Page<BookDTOEasy> readAllBooks(Pageable pageable) {
        Page<Book> entityPage = booksRepository.findAll(pageable);
        return entityPage.map(entity -> mapperToDTOEasy(entity, true));
    }

    public BookDTORich readOneById(Long id) {
        return mapperToDTORich(getById(id), true);
    }

    public Page<BookDTORich> readBooksByNameStartingWith(String name, Pageable pageable) {

        String ex = String.format(("Книга, начинающаяся на = %s не найдена"), name);

        Page<Book> entityPage = booksRepository.findBooksByNameIgnoreCaseStartingWithOrderByAuthorOwner(name, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityPage.map(entity -> mapperToDTORich(entity, true));

    }

    public Page<BookDTORich> readBooksByPersonOwnerId(Person personOwner, Pageable pageable) {

        String ex = String.format(("У читателя с id = %s нет книг"), personOwner.getId());

        Page<Book> entityPage = booksRepository.findBooksByPersonOwner(personOwner, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityPage.map(entity -> mapperToDTORich(entity, true));
    }

    public Page<BookDTORich> readBooksByAuthorOwner(Author authorOwner, Pageable pageable) {
        //String ex = String.format(("У читателя = %s нет книг"), authorOwner);

        authorService.readOneById(authorOwner.getId());
        Page<Book> entityPage = booksRepository.findBooksByAuthorOwner(authorOwner, pageable);
//
//        if (entityPage.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }

        return entityPage.map(entity -> mapperToDTORich(entity, true));
    }

    public List<BookDTORich> readBooksIsFree() {

        List<Book> entityList = booksRepository.findBooksIsFree();

        return entityList.stream().map(entity -> mapperToDTORich(entity, true)).collect(Collectors.toList());

    }


    public List<BookDTORich> readBooksIsNotFree() {

        List<Book> entityList = booksRepository.findAll().stream().filter(book -> !(book.isBookIsFree())).toList();

        return entityList.stream().map(entity -> mapperToDTORich(entity, true)).collect(Collectors.toList());

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

    public Book mapperToEntityRich(BookDTORich view, boolean isWrite) {
        Book entity = new Book();

        if(isWrite) {
            entity.setId(view.getId());
        }
        if (view.getAuthorOwner() != null) {
            authorService.readOneById(view.getAuthorOwner().getId());
            entity.setAuthorOwner(authorService.mapperToEntity(view.getAuthorOwner(), true));
        }

        if (view.getPersonOwnerEasy() != null) {
            peopleService.readOneById(view.getPersonOwnerEasy().getId());
            entity.setPersonOwner(peopleService.mapperToEntityEasy(view.getPersonOwnerEasy(), true));

        }
        if (view.getPersonOwnerEasy() == null) {
            entity.setBookIsFree(true);
        } else entity.setBookIsFree(false);

        entity.setName(view.getName());
        entity.setYearOfPublication(view.getYearOfPublication());


        return entity;

    }

    public BookDTORich mapperToDTORich(Book entity, boolean isWrite) {

        BookDTORich view = new BookDTORich();
        if(isWrite) {
            view.setId(entity.getId());
        }
        if(entity.getAuthorOwner() != null){
            authorService.readOneById(entity.getAuthorOwner().getId());
            view.setAuthorOwner(authorService.mapperToDTO(entity.getAuthorOwner(), true));
        }

        if (entity.getPersonOwner() != null) {
            peopleService.readOneById(entity.getPersonOwner().getId());
            view.setPersonOwnerEasy(peopleService.mapperToDTOEasy(entity.getPersonOwner(), true));

        }

        if (entity.getPersonOwner() == null) {
            view.setBookIsFree(true);
        }else{
            view.setBookIsFree(false);
        }

        view.setName(entity.getName());
        view.setYearOfPublication(entity.getYearOfPublication());

        return view;
    }

    public Book mapperToEntityEasy(BookDTOEasy view, boolean isWrite) {
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

    public BookDTOEasy mapperToDTOEasy(Book entity, boolean isWrite) {

        BookDTOEasy view = new BookDTOEasy();
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
