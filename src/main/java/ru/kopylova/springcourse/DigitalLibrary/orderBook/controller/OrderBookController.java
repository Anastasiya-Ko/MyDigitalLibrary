package ru.kopylova.springcourse.DigitalLibrary.orderBook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTOEasy;
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTORich;
import ru.kopylova.springcourse.DigitalLibrary.orderBook.service.OrderBooksService;

import java.util.List;

@RestController
@RequestMapping("/order-book")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Контроллер для оборота книг")
public class OrderBookController {

    OrderBooksService orderBooksService;

    @PutMapping("/release/{bookId}")
    @Operation(summary = "Метод для освобождения книги")
    public String release(@PathVariable Long bookId) {
        return orderBooksService.release(bookId);
    }

    @PutMapping("/assign")
    @Operation(summary = "Назначение книги читателю")
    public String assign(@RequestParam("book-id") Long bookId, @RequestParam("reader-id") Long readerId) {
        return orderBooksService.assignBookByReader(bookId, readerId);
    }

    @GetMapping("/free")
    @Operation(summary = "Чтение свободных книг")
    public List<BookDTOEasy> readBooksFree() {
        return orderBooksService.readBooksFree();
    }

    @GetMapping("/busy")
    @Operation(summary = "Чтение книг, взятых читателями")
    public List<BookDTORich> readBooksBusy() {
        return orderBooksService.readBooksBusy();
    }

    @GetMapping("/by-person-owner-id/{readerId}")
    @Operation(summary = "Чтение книг, взятых конкретным читателем")
    public List<BookDTOEasy> readBooksByReaderOwner(@PathVariable Long readerId) {
        return orderBooksService.readBooksByReaderOwnerId(readerId);
    }
}


