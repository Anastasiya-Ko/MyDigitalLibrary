package ru.kopylova.springcourse.DigitalLibrary.services;

import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.models.view.AuthorDTO;

@Service
public class AuthorService {
    public Author mapperToEntity(AuthorDTO view) {
        Author entity = new Author();

        entity.setName(view.getName());
     //   entity.setBooks(view.getBooks());

        return entity;

    }

    public AuthorDTO mapperToDTO(Author entity) {

        AuthorDTO view = new AuthorDTO();

        view.setName(entity.getName());
    //    view.setBooks(entity.getBooks());

        return view;
    }
}
