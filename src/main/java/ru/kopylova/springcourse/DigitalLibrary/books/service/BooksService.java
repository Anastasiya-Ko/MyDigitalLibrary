package ru.kopylova.springcourse.DigitalLibrary.books.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BooksService {

    BooksRepository booksRepository;
    BookMapper bookMapper;
    AuthorService authorService;


    public BookDTOEasy createBook(BookDTOEasy view) {

        availableAuthors(view);
        Book entity = bookMapper.mapperToEntity(view, false);
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

    public List<BookDTOEasy> readBooksWriteGroupAuthors() {
        List<Book> entityList = booksRepository.findBooksWriteGroupAuthors();
        entityList.sort(Comparator.comparing(Book::getTitle));
        return entityList.stream().map(entity -> bookMapper.mapperToDTOEasy(entity, true)).collect(Collectors.toList());
    }

    public List<BookDTOEasy> readBooksWriteRequestAuthor(Long authorId) {
        authorService.readOneById(authorId);
        List<Book> entityList = booksRepository.findBooksWriteRequestAuthor(authorId);
        entityList.sort(Comparator.comparing(Book::getTitle));
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
