package ru.kopylova.springcourse.DigitalLibrary.authors.controllers;

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


@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorsController {

    AuthorService authorService;

    @PostMapping
    public AuthorDTO createAuthor(@Valid @RequestBody AuthorDTO view) {
        return authorService.createAuthor(view);
    }

    @PutMapping()
    public AuthorDTO updateAuthor(@Valid @RequestBody AuthorDTO view) {
        return authorService.updateAuthor(view);
    }
    @GetMapping("/all")
    public Page<AuthorDTO> readAllAuthors(Pageable pageable) {
        return authorService.readAll(pageable);
    }

    @GetMapping("/one/{id}")
    public AuthorDTO readAuthorById(@PathVariable Long id) {
        return authorService.readOneById(id);
    }

    @GetMapping("/has-no-books")
    public List<AuthorDTO> readAuthorHasNoBooks() {
        return authorService.readAuthorHasNoBooks();
    }

    @DeleteMapping("/{id}")
    public String deleteAuthorById(@PathVariable Long id) {
        return authorService.deleteAuthorById(id);
    }

}
