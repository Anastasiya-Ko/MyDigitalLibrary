package ru.kopylova.springcourse.DigitalLibrary.reports;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.models.view.BookDTOReport;
import ru.kopylova.springcourse.DigitalLibrary.repositories.BooksRepository;
import ru.kopylova.springcourse.DigitalLibrary.services.ReadersService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookDataReport {
    BooksRepository booksRepository;
    ReadersService readersService;

    public BookDTOReport readBookReportById(Long id) {

        Book entity = booksRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Книга с таким id отсутствует"));

        BookDTOReport view = new BookDTOReport();
        view.setBookId(entity.getId());
        view.setTitle(entity.getTitle());
        view.setYearOfPublication(entity.getYearOfPublication());
        view.setAuthorId(entity.getAuthorOwner().getId());
        view.setAuthorName(entity.getAuthorOwner().getName());

        if (entity.getReaderOwner() == null) {
            view.setReaderId("Книга свободна");
            view.setReaderFirstName("Книга свободна");
            view.setReaderLastName("Книга свободна");
        } else {
            view.setReaderId(String.valueOf(entity.getReaderOwner().getId()));
            view.setReaderFirstName(entity.getReaderOwner().getFirstName());
            view.setReaderLastName(entity.getReaderOwner().getLastName());
        }

        return view;

    }
}
