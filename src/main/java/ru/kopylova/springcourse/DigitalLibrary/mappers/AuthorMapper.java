package ru.kopylova.springcourse.DigitalLibrary.mappers;

import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.models.view.AuthorDTO;

@Service
public class AuthorMapper {

    public Author mapperToEntity(AuthorDTO view, boolean isWrite) {
        Author entity = new Author();

        if(isWrite) {
            entity.setId(view.getId());
        }

        entity.setName(view.getName());

        return entity;

    }

    public AuthorDTO mapperToDTO(Author entity, boolean isWrite) {

        AuthorDTO view = new AuthorDTO();

        if(isWrite) {
            view.setId(entity.getId());
        }
        view.setName(entity.getName());

        return view;
    }

}
