package ru.kopylova.springcourse.DigitalLibrary.mappers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.models.view.BookDTOEasy;
import ru.kopylova.springcourse.DigitalLibrary.models.view.BookDTORich;
import ru.kopylova.springcourse.DigitalLibrary.services.AuthorService;
import ru.kopylova.springcourse.DigitalLibrary.services.ReadersService;

/**
 * Побороться с зацикливанием программы помогла комбинация аннотаций -
 *    // @Autowired
 *    // @NonFinal
 *    // @Lazy
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookMapper {

    private AuthorMapper authorMapper;

    private AuthorService authorService;

    private ReadersService readersService;

    private ReaderMapper readerMapper;


    public Book mapperToEntityRich(BookDTORich view, boolean isWrite) {
        Book entity = new Book();

        if(isWrite) {
            entity.setId(view.getId());
        }
        if (view.getAuthorOwner() != null) {
            authorService.readOneById(view.getAuthorOwner().getId());
            entity.setAuthorOwner(authorMapper.mapperToEntity(view.getAuthorOwner(), true));
        }

        if (view.getReaderDTOEasy() != null) {
            readersService.readOneById(view.getReaderDTOEasy().getId());
            entity.setReaderOwner(readerMapper.mapperToEntityEasy(view.getReaderDTOEasy(), true));

        }

        entity.setBookIsFree(view.getReaderDTOEasy() == null);

        entity.setTitle(view.getTitle());
        entity.setYearOfPublication(view.getYearOfPublication());


        return entity;

    }

    public Book mapperToEntityEasy(BookDTOEasy view, boolean isWrite) {
        Book entity = new Book();

        if(isWrite) {
            entity.setId(view.getId());
        }
        if (view.getAuthorOwner() != null) {
            authorService.readOneById(view.getAuthorOwner().getId());
            entity.setAuthorOwner(authorMapper.mapperToEntity(view.getAuthorOwner(), true));
        }

        entity.setTitle(view.getTitle());
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
            view.setAuthorOwner(authorMapper.mapperToDTO(entity.getAuthorOwner(), true));
        }

        if (entity.getReaderOwner() != null) {
            readersService.readOneById(entity.getReaderOwner().getId());
            view.setReaderDTOEasy(readerMapper.mapperToDTOEasy(entity.getReaderOwner(), true));

        }

        view.setBookIsFree(entity.getReaderOwner() == null);

        view.setTitle(entity.getTitle());
        view.setYearOfPublication(entity.getYearOfPublication());

        return view;
    }

    public BookDTOEasy mapperToDTOEasy(Book entity, boolean isWrite) {

        BookDTOEasy view = new BookDTOEasy();
        if(isWrite) {
            view.setId(entity.getId());
        }

        if(entity.getAuthorOwner() != null){
            authorService.readOneById(entity.getAuthorOwner().getId());
            view.setAuthorOwner(authorMapper.mapperToDTO(entity.getAuthorOwner(), true));
        }

        view.setTitle(entity.getTitle());
        view.setYearOfPublication(entity.getYearOfPublication());

        return view;
    }
}
