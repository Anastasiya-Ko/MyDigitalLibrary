package ru.kopylova.springcourse.DigitalLibrary.reports;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.models.view.BookDTOReport;
import ru.kopylova.springcourse.DigitalLibrary.repositories.BooksRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookDataService {

    BooksRepository booksRepository;

    public BookDTOReport createDTO(Book entity) {


        BookDTOReport view = new BookDTOReport();

        view.setBookId(entity.getId().toString());
        view.setTitle(entity.getTitle());
        view.setYearOfPublication("" + entity.getYearOfPublication().getYear());
        view.setAuthorId(entity.getAuthorOwner().getId().toString());
        view.setAuthorName(entity.getAuthorOwner().getName());

        if (entity.getReaderOwner() != null) {
            view.setReaderId(String.valueOf(entity.getReaderOwner().getId()));
            view.setReaderFirstName(entity.getReaderOwner().getFirstName());
            view.setReaderLastName(entity.getReaderOwner().getLastName());
        }

        return view;
    }

    public List<BookDTOReport> createListDTO() {

        var list = booksRepository.findAll();
        list.sort(Comparator.comparing(Book::getId));

        List<BookDTOReport> listResult = new ArrayList<>();

        for (Book book : list){
            var temp = createDTO(book);
            listResult.add(temp);
        }

        return listResult;
    }
}
