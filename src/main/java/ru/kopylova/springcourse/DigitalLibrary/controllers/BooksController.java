package ru.kopylova.springcourse.DigitalLibrary.controllers;

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
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Reader;
import ru.kopylova.springcourse.DigitalLibrary.models.view.BookDTOEasy;
import ru.kopylova.springcourse.DigitalLibrary.models.view.BookDTORich;
import ru.kopylova.springcourse.DigitalLibrary.services.BooksService;
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
    public BookDTORich createBook(@Valid @RequestBody BookDTORich view) {
        return booksService.createBook(view);
    }

    @PutMapping()
    public BookDTORich updateBook(@Valid @RequestBody BookDTORich view) {
        return booksService.updateBook(view);
    }

    @GetMapping("/all")
    public Page<BookDTOEasy> readAllBooks(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "5") @Min(1) @Max(100) Integer limit,
            @RequestParam(value = "sort", defaultValue = "NAME_ASC") BookSort sort
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

    @GetMapping("/by-person-owner-id/{id}")
    public Page<BookDTORich> readBooksByReaderOwner(@PathVariable Reader id, Pageable pageable) {
        return booksService.readBooksByReaderOwnerId(id, pageable);
    }

    @GetMapping("/by-author-owner-id")
    public Page<BookDTORich> readBooksByAuthorOwner(@RequestParam("author-owner-id") Author authorOwner, Pageable pageable) {
        return booksService.readBooksByAuthorOwnerId(authorOwner, pageable);
    }

    @GetMapping("/books-are-free")
    public List<BookDTORich> readBooksAreFree() {
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

}
