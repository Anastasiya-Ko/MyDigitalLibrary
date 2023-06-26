package ru.kopylova.springcourse.DigitalLibrary.orderBook.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.readers.service.ReadersService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderBooksService {

    ReadersService readersService;
    JdbcTemplate jdbcTemplate;

//    /**
//     * Метод для освобождения книги, при возвращении её в библиотеку
//     * @param bookId возвращаемой книги
//     */
//    public String release(Long bookId) {
//        Book entity = getById(bookId);
//        if (!entity.getReaders().isEmpty()) {
//            jdbcTemplate.update("DELETE FROM book_reader WHERE book_id=?", bookId);
//            return String.format("Читатель %s вернул в библиотеку книгу \"%s\", автора(ов) %s",
//                    entity.getReadersOwner().getLastName()
//                            .concat(" ")
//                            .concat(String.valueOf(entity.getReadersOwner().getFirstName().charAt(0)))
//                            .concat("."),
//                    entity.getTitle(), entity.getAuthors()
//                            .stream().map(Author::getName)
//                            .collect(Collectors.joining(", ")));
//        } else return "Книга не может быть освобождена. Она хранится в библиотеке";
//    }
//
//    /**
//     * Метод для назначения книги читателю
//     * @param bookId   назначаемая книга
//     * @param readerId читатель, берущий книгу
//     * @return информационное сообщение
//     */
//    public String assignBookByReader(Long bookId, Long readerId) {
//        Book entityBook = getById(bookId);
//        if (entityBook.isBookIsFree()) {
//            readersService.readOneById(readerId);
//            jdbcTemplate.update("UPDATE book SET reader_id=?, is_free=false WHERE id=?", readerId, bookId);
//            return String.format("Читатель %s взял книгу \"%s\", автора(ов) %s ",
//                    readersService.readOneById(readerId).getLastName()
//                            .concat(" ")
//                            .concat(String.valueOf(readersService.readOneById(readerId).getFirstName().charAt(0)))
//                            .concat("."),
//                    entityBook.getTitle(),
//                    entityBook.getAuthors()
//                            .stream().map(Author::getName)
//                            .collect(Collectors.joining(", ")));
//        } else return "Книга не может быть выдана. Она занята другим читателем";
//
//    }
//
//    public List<BookDTOEasy> readBooksFree() {
//
//        List<Book> entityList = booksRepository.findBooksAreFree();
//
//        return entityList.stream().map(entity -> bookMapper.mapperToDTOEasy(entity, true)).collect(Collectors.toList());
//
//    }
//
//    public List<BookDTORich> readBooksBusy() {
//
//        List<Book> entityList = booksRepository.findAll().stream().filter(book -> !(book.isBookIsFree())).toList();
//
//        return entityList.stream().map(entity -> bookMapper.mapperToDTORich(entity, true)).collect(Collectors.toList());
//
//    }
//public Page<BookDTORich> readBooksByReaderOwnerId(Reader readerOwner, Pageable pageable) {
//
//    String ex = String.format("Читатель %s не имеет книг из библиотеки", readerOwner.getLastName()
//            .concat(" ")
//            .concat(String.valueOf(readerOwner.getFirstName().charAt(0)))
//            .concat("."));
//
//    Page<Book> entityPage = booksRepository.findBooksByReaderOwner(readerOwner, pageable);
//
//    if (entityPage.isEmpty()) {
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
//    }
//
//    return entityPage.map(entity -> bookMapper.mapperToDTORich(entity, true));
//}

}
