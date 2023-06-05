package ru.kopylova.springcourse.DigitalLibrary.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Reader;
import ru.kopylova.springcourse.DigitalLibrary.models.view.BookDTOEasy;
import ru.kopylova.springcourse.DigitalLibrary.models.view.BookDTORich;
import ru.kopylova.springcourse.DigitalLibrary.reports.BookReportService;
import ru.kopylova.springcourse.DigitalLibrary.services.BooksService;
import ru.kopylova.springcourse.DigitalLibrary.util.page.sort.BookSort;

import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BooksController {


    BookReportService bookReportService;
    BooksService booksService;


    @PostMapping
    public BookDTOEasy createBook(@Valid @RequestBody BookDTOEasy view) {
        return booksService.createBook(view);
    }

    @PutMapping("/update")
    public BookDTOEasy updateBook(@Valid @RequestBody BookDTOEasy view) {
        return booksService.updateBook(view);
    }

    @PutMapping("/release/{id}")
    public String release(@PathVariable Long id) {
        return booksService.release(id);
    }

    @PutMapping("/assign")
    public String assign(@RequestParam("book-id") Long bookId, @RequestParam("reader-id") Long readerId) {
        return booksService.assignBookByReader(bookId, readerId);
    }

    @GetMapping("/all")
    public Page<BookDTOEasy> readAllBooks(
            @RequestParam(value = "offset") @Min(0) Integer offset,
            @RequestParam(value = "limit") @Min(1) @Max(100) Integer limit,
            @RequestParam(value = "sort") BookSort sort
    ) {
        return booksService.readAllBooks(
                PageRequest.of(offset, limit, sort.getSortValue()));
    }

    @GetMapping("/one/{id}")
    public BookDTORich readOneBookById(@PathVariable @Min(0) Long id) {
        return booksService.readOneById(id);
    }

    @GetMapping("/starts-title")
    public Page<BookDTOEasy> readBooksByNameStartingWith(@RequestParam("title") String title, Pageable pageable) {
        return booksService.readBooksByTitleStartingWith(title, pageable);
    }

    @GetMapping("/by-person-owner-id")
    public Page<BookDTORich> readBooksByReaderOwner(@Valid @RequestBody Reader reader, Pageable pageable) {
        return booksService.readBooksByReaderOwnerId(reader, pageable);
    }

    @GetMapping("/by-author-owner-id")
    public Page<BookDTOEasy> readBooksByAuthorOwner(@Valid @RequestBody Author authorOwner, Pageable pageable) {
        return booksService.readBooksByAuthorOwnerId(authorOwner, pageable);
    }

    @GetMapping("/books-are-free")
    public List<BookDTOEasy> readBooksAreFree() {
        return booksService.readBooksAreFree();
    }

    @GetMapping("/books-are-not-free")
    public List<BookDTORich> readBooksAreNotFree() {
        return booksService.readBooksAreNotFree();
    }

    @DeleteMapping("/{id}")
    public String deleteBookById(@PathVariable Long id) {
        return booksService.deleteBookById(id);
    }

    @GetMapping("/create-xlsx")
    public void createReportAllBooks(HttpServletResponse response) throws IOException {


        byte[] bytes = bookReportService.createReportAllBooks();

        response.setContentType("application/octet-stream");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = allBooks.xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
        response.setCharacterEncoding("UTF-8");
    }

}
