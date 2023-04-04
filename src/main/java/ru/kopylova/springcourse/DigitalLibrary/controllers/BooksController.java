package ru.kopylova.springcourse.DigitalLibrary.controllers;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.services.BooksService;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BooksController {

    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping("/all")
    public List<Book> getAll(@PageableDefault Pageable pageable, @SortDefault Sort sort) {
        return booksService.findAll(pageable, sort);
    }
}
