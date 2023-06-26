package ru.kopylova.springcourse.DigitalLibrary.orderBook.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kopylova.springcourse.DigitalLibrary.orderBook.service.OrderBooksService;

@RestController
@RequestMapping("/order-book")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderBookController {

    OrderBooksService orderBooksService;

//    @PutMapping("/release/{bookId}")
//    public String release(@PathVariable Long bookId) {
//        return orderBooksService.release(bookId);
//    }
//
//    @PutMapping("/assign")
//    public String assign(@RequestParam("book-id") Long bookId, @RequestParam("reader-id") Long readerId) {
//        return booksService.assignBookByReader(bookId, readerId);
//    }
//@GetMapping("/free")
//public List<BookDTOEasy> readBooksFree() {
//    return booksService.readBooksFree();
//}
//
//    @GetMapping("/busy")
//    public List<BookDTORich> readBooksBusy() {
//        return booksService.readBooksBusy();
//    }
//@GetMapping("/by-person-owner-id")
//public Page<BookDTORich> readBooksByReaderOwner(@Valid @RequestBody Reader reader, Pageable pageable) {
//    return booksService.readBooksByReaderOwnerId(reader, pageable);
}


