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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookDataReport {
    BooksRepository booksRepository;

    public BookDTOReport readBookReportById(Long id) {

        Book entity = booksRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Книга с таким id отсутствует"));

        BookDTOReport view = new BookDTOReport();
        view.setBookId(entity.getId());
        view.setTitle(entity.getTitle());
        view.setYearOfPublication(entity.getYearOfPublication());

        return view;

    }
}
