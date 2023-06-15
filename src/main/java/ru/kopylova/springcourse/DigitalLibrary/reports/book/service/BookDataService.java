package ru.kopylova.springcourse.DigitalLibrary.reports.book.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.books.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.books.repository.BooksRepository;
import ru.kopylova.springcourse.DigitalLibrary.reports.book.models.view.BookDTOReport;

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
        StringBuilder tempNameAuthor =new StringBuilder();

        view.setBookId(entity.getId().toString());
        view.setTitle(entity.getTitle());
        view.setYearOfPublication("" + entity.getYearOfPublication().getYear());

        for (int i = 0; i < entity.getAuthors().size(); i++) {
            tempNameAuthor.append(entity.getAuthors().get(i).getName()+"\n");
        }
        view.setAuthorName("" + tempNameAuthor);

        if (entity.getReaderOwner() != null) {
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
