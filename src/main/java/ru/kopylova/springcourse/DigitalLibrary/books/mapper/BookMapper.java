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
        entity.setYearOfPublication(view.getYearOfPublication());

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


        view.setReaderDTOEasy(readerMapper.mapperToDTOEasy(entity.getReaderOwner(), true));


        view.setBookIsFree(entity.getReaderOwner() == null);

        view.setTitle(entity.getTitle());
        view.setYearOfPublication(entity.getYearOfPublication());

        return view;
    }

    public BookDTOEasy mapperToDTOEasy(Book entity, boolean isWrite) {

        BookDTOEasy view = new BookDTOEasy();
        if (isWrite) {
            view.setId(entity.getId());
        }

        view.setAuthorsOwner(entity.getAuthors()
                .stream().map(author -> authorMapper.mapperToDTO(author, true))
                .collect(Collectors.toList()));

        view.setTitle(entity.getTitle());
        view.setYearOfPublication(entity.getYearOfPublication());

        return view;
    }
}

//           entity.setAuthors(authorMapper.mapperToEntity(view.getAuthorOwner, true));
//            view.setAuthorOwner(authorMapper.mapperToDTO(entity.getAuthorOwner(), true));