package ru.kopylova.springcourse.DigitalLibrary.books.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.authors.mapper.AuthorMapper;
import ru.kopylova.springcourse.DigitalLibrary.books.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTOEasy;
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTORich;
import ru.kopylova.springcourse.DigitalLibrary.readers.mapper.ReaderMapper;

import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * Побороться с зацикливанием программы помогла комбинация аннотаций -
 * // @Autowired
 * // @NonFinal
 * // @Lazy
 */

/**
 * Сервис для работы с книгами
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookMapper {

    /**
     * Подгрузка класса для маппинга автора
     */
    private AuthorMapper authorMapper;
    /**
     * Подгрузка класса для маппинга читателя
     */
    private ReaderMapper readerMapper;

    /**
     * Сопоставление данных из дто, данным энтити
     * @param view представление книги
     * @param isWrite отвечает за возможность записи id
     * @return сущность Книга
     */
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


        return entity;
    }

    /**
     * Сопоставление данных из сущности книги, данным в дто(с максимальным набором полей)
     * Используется при поиске книги по id
     * @param entity сущности книги
     * @param isWrite возможности записи id
     * @return дто книги, с максимальным набором полей
     */
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

        return view;
    }

    /**
     * Сопоставление данных из сущности книги, данным в дто(с набором основных полей)
     * @param entity сущности книги
     * @param isWrite возможности записи id
     * @return дто книги, с набором основных полей
     */
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

        return view;
    }
}
