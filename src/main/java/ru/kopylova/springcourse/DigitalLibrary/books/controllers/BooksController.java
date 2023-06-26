package ru.kopylova.springcourse.DigitalLibrary.books.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTOEasy;
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTORich;
import ru.kopylova.springcourse.DigitalLibrary.books.service.BooksService;
import ru.kopylova.springcourse.DigitalLibrary.util.page.sort.BookSort;

import java.util.List;

@Validated
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BooksController {

    BooksService booksService;

    @PostMapping
    public BookDTOEasy createBook(@Valid @RequestBody BookDTOEasy view) {
        return booksService.createBook(view);
    }

    @PutMapping("/update")
    public BookDTOEasy updateBook(@Valid @RequestBody BookDTOEasy view) {
        return booksService.updateBook(view);
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

    @GetMapping("/write-group-authors")
    public List<BookDTOEasy> readBooksWriteGroupAuthors() {
        return booksService.readBooksWriteGroupAuthors();
    }

    @DeleteMapping("/{id}")
    public String deleteBookById(@PathVariable Long id) {
        return booksService.deleteBookById(id);
    }

}
