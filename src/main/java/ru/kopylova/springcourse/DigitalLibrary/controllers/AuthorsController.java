package ru.kopylova.springcourse.DigitalLibrary.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kopylova.springcourse.DigitalLibrary.models.view.AuthorDTO;
import ru.kopylova.springcourse.DigitalLibrary.services.AuthorService;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorsController {

    AuthorService authorService;

    @GetMapping("/all")
    public Page<AuthorDTO> readAllAuthors(Pageable pageable) {
        return authorService.readAll(pageable);
    }

    @GetMapping("/one/{id}")
    public AuthorDTO readAuthorById(@PathVariable Long id) {
        return authorService.readOneById(id);
    }

}
