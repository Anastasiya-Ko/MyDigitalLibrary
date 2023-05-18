package ru.kopylova.springcourse.DigitalLibrary.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Person;
import ru.kopylova.springcourse.DigitalLibrary.models.view.BookDTO;
import ru.kopylova.springcourse.DigitalLibrary.services.BooksService;
import ru.kopylova.springcourse.DigitalLibrary.util.page.sort.BookSort;

@RestController
@RequestMapping("/book")
public class BooksController {

    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @PostMapping
    public BookDTO createBook(@Valid @RequestBody BookDTO view) {
        return booksService.createBook(view);
    }

    @PutMapping()
    public BookDTO updateBook(@Valid @RequestBody BookDTO view) {
        return booksService.updateBook(view);
    }

    @GetMapping("/all")
    public Page<BookDTO> readAllBooks(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "5") @Min(1) @Max(100) Integer limit,
            @RequestParam(value = "sort", defaultValue = "NAME_ASC") BookSort sort
    ) {
        return booksService.readAllBooks(
                PageRequest.of(offset, limit, sort.getSortValue()));
    }

    @GetMapping("/one/{id}")
    public BookDTO readOneBookById(@PathVariable Long id) {
        return booksService.readOneById(id);
    }

    @GetMapping("/starts-name")
    public Page<BookDTO> readBooksByNameStartingWith(@RequestParam("name-book") String name, Pageable pageable) {
        return booksService.readBooksByNameStartingWith(name, pageable);
    }

    @GetMapping("/by-person-owner-id")
    public Page<BookDTO> readBooksByPersonOwner(@RequestParam("person-owner-id")Person personOwner, Pageable pageable) {
        return booksService.readBooksByPersonOwnerId(personOwner, pageable);
    }

    @GetMapping("/by-author-owner")
    public Page<BookDTO> readBooksByAuthorOwner(@RequestParam("author-owner") Author authorOwner, Pageable pageable) {
        return booksService.readBooksByAuthorOwner(authorOwner, pageable);
    }

    @DeleteMapping("/{id}")
    public String deleteBookById(@PathVariable Long id) {
        return booksService.deleteBookById(id);
    }

}
