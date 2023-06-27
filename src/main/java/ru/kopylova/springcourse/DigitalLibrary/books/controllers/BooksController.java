package ru.kopylova.springcourse.DigitalLibrary.books.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import ru.kopylova.springcourse.DigitalLibrary.dictionary.BookSort;

import java.util.List;

@Validated
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Контроллер Книги", description = "Взаимодействие со справочником книг")
public class BooksController {

    /**
     * Подгрузка сервиса для работы с книгами
     */
    BooksService booksService;

    @PostMapping
    @Operation(summary = "Занесение новой книги в БД")
    public BookDTOEasy createBook(@Valid @RequestBody BookDTOEasy view) {
        return booksService.createBook(view);
    }

    @PutMapping("/update")
    @Operation(summary = "Обновление в БД ранее сохранённой книги")
    public BookDTOEasy updateBook(@Valid @RequestBody BookDTOEasy view) {
        return booksService.updateBook(view);
    }


    @GetMapping("/all")
    @Operation(summary = "Постраничный вывод справочника Книги", description = "с настройками отображения страницы")
    public Page<BookDTOEasy> readAllBooks(
            @RequestParam(value = "offset") @Parameter(description = "Страница") @Min(0) Integer offset,
            @RequestParam(value = "limit") @Parameter(description = "Количество книг на 1 странице")
            @Min(1) @Max(100) Integer limit,
            @RequestParam(value = "sort") @Parameter(description = "Возможная сортировка") BookSort sort
    ) {
        return booksService.readAllBooks(
                PageRequest.of(offset, limit, sort.getSortValue()));
    }

    @GetMapping("/one/{bookId}")
    @Operation(summary = "Чтение книги по её идентификатору")
    public BookDTORich readOneBookById(
            @PathVariable @Parameter(description = "id книги должно быть положительным числом") @Min(0) Long bookId) {
        return booksService.readOneById(bookId);
    }

    @GetMapping("/starts-title")
    @Operation(summary = "Чтение книг по началу названия")
    public Page<BookDTOEasy> readBooksByNameStartingWith(@RequestParam("title") String title, Pageable pageable) {
        return booksService.readBooksByTitleStartingWith(title, pageable);
    }

    @GetMapping("/write-group-authors")
    @Operation(summary = "Чтение книг, написанных группой авторов")
    public List<BookDTOEasy> readBooksWriteGroupAuthors() {
        return booksService.readBooksWriteGroupAuthors();
    }

    @GetMapping("/write-authors/{authorId}")
    @Operation(summary = "Чтение книг, принадлежащих запрашиваемому автору", description = "по id автора")
    public List<BookDTOEasy> readBooksWriteRequestAuthor(@PathVariable Long authorId) {
        return booksService.readBooksWriteRequestAuthor(authorId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление автора", description = "по id")
    public String deleteBookById(@PathVariable Long id) {
        return booksService.deleteBookById(id);
    }

}
