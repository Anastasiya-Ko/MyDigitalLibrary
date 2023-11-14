package ru.kopylova.springcourse.DigitalLibrary.authors.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.kopylova.springcourse.DigitalLibrary.authors.models.view.AuthorDTO;
import ru.kopylova.springcourse.DigitalLibrary.authors.service.AuthorService;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Контроллер Авторы", description = "Взаимодействие со справочником Авторы")
public class AuthorsController {

    AuthorService authorService;

    @PostMapping
    @Operation(summary = "Внесение в базу нового автора")
    public AuthorDTO createAuthor(@Valid @RequestBody AuthorDTO view) {
        return authorService.createAuthor(view);
    }

    @PutMapping()
    @Operation(summary = "Редактирование полей уже существующего автора")
    public AuthorDTO updateAuthor(@Valid @RequestBody AuthorDTO view) {
        return authorService.updateAuthor(view);
    }

    @GetMapping("/all")
    @Operation(summary = "Постраничный вывод информации об авторах")
    public Page<AuthorDTO> readAllAuthors(Pageable pageable) {
        return authorService.readAll(pageable);
    }

    @GetMapping("/one/{authorId}")
    @Operation(summary = "Поиск автора по его id")
    public AuthorDTO readAuthorById(@PathVariable @Parameter(description = "id искомого автора") Long authorId) {
        return authorService.readOneById(authorId);
    }

    @GetMapping("/has-no-books")
    @Operation(summary = "Отображение авторов, книги которых не представлены в библиотеке")
    public List<AuthorDTO> readAuthorHasNoBooks() {
        return authorService.readAuthorHasNoBooks();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление автора")
    public String deleteAuthorById(@PathVariable @Parameter(description = "id удаляемого автора") Long id) {
        return authorService.deleteAuthorById(id);
    }

}
