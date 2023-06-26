package ru.kopylova.springcourse.DigitalLibrary.books.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.authors.mapper.AuthorMapper;
import ru.kopylova.springcourse.DigitalLibrary.authors.service.AuthorService;
import ru.kopylova.springcourse.DigitalLibrary.books.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTOEasy;
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTORich;
import ru.kopylova.springcourse.DigitalLibrary.readers.mapper.ReaderMapper;
import ru.kopylova.springcourse.DigitalLibrary.readers.service.ReadersService;

import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * Побороться с зацикливанием программы помогла комбинация аннотаций -
 * // @Autowired
 * // @NonFinal
 * // @Lazy
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookMapper {

    private AuthorMapper authorMapper;

    private AuthorService authorService;

    private ReadersService readersService;

    private ReaderMapper readerMapper;

    public Book mapperToEntity(BookDTOEasy view, boolean isWrite) {
        Book entity = new Book();

        if (isWrite) {
            entity.setId(view.getId());
        }

        entity.setAuthors(view.getAuthorsOwner().stream()
                .map(authorDTO -> authorMapper.mapperToEntity(authorDTO, true))
                .collect(Collectors.toList()));

        entity.setTitle(view.getTitle());

        if (view.getYearOfPublication() <= LocalDate.now().getYear()) {
            entity.setYearOfPublication(view.getYearOfPublication());
        } else throw new RuntimeException("Год издания книги должен быть меньше или равен текущему году");

        entity.setAvailableNumbers(view.getAvailableNumbers());

        return entity;
    }

    public BookDTORich mapperToDTORich(Book entity, boolean isWrite) {

        BookDTORich view = new BookDTORich();
        if (isWrite) {
            view.setId(entity.getId());
        }

        view.setAuthorsOwner(entity.getAuthors()
                .stream().map(author -> authorMapper.mapperToDTO(author, true))
                .collect(Collectors.toList()));

        if(!entity.getReaders().isEmpty()) {
            view.setReadersOwner(entity.getReaders()
                    .stream().map(reader -> readerMapper.mapperToDTOEasy(reader, true))
                    .collect(Collectors.toList()));
        }
        view.setTitle(entity.getTitle());
        view.setYearOfPublication(entity.getYearOfPublication());
        view.setAvailableNumbers(entity.getAvailableNumbers());

        return view;
    }

    public BookDTOEasy mapperToDTOEasy(Book entity, boolean isWrite) {

        BookDTOEasy view = new BookDTOEasy();
        if (isWrite) {
            view.setId(entity.getId());
        }

        view.setTitle(entity.getTitle());
        view.setAuthorsOwner(entity.getAuthors()
                .stream().map(author -> authorMapper.mapperToDTO(author, true))
                .collect(Collectors.toList()));
        view.setYearOfPublication(entity.getYearOfPublication());
        view.setAvailableNumbers(entity.getAvailableNumbers());

        return view;
    }
}
