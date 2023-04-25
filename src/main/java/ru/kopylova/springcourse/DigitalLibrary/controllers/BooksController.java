package ru.kopylova.springcourse.DigitalLibrary.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kopylova.springcourse.DigitalLibrary.services.BooksService;

@RestController
@RequestMapping("/book")
public class BooksController {

    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

//    @GetMapping("/all")
//    public List<Book> getAll(@PageableDefault Pageable pageable, @SortDefault Sort sort) {
//        return booksService.findAll(pageable, sort);
//    }
//    @PostMapping
//    public BookDTO create (@RequestBody BookDTO view){
//        return booksService.create(view);
//    }
}
