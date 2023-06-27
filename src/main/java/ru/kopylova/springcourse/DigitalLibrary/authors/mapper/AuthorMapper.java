package ru.kopylova.springcourse.DigitalLibrary.authors.mapper;

import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.authors.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.authors.models.view.AuthorDTO;

/**
 * Маппинг сущности Автор
 */
@Service
public class AuthorMapper {

    /**
     * Сопоставление данных дто, данным сущности автора
     * @param view представление автора
     * @param isWrite отвечает за запись id автора
     * @return сущность автора
     */
    public Author mapperToEntity(AuthorDTO view, boolean isWrite) {
        Author entity = new Author();

        if(isWrite) {
            entity.setId(view.getId());
        }

        entity.setName(view.getName());

        return entity;

    }

    /**
     * Сопоставление данных сущности, данным дто
     * @param entity сущности автора
     * @param isWrite отвечает за запись id автора
     * @return представление автора
     */
    public AuthorDTO mapperToDTO(Author entity, boolean isWrite) {

        AuthorDTO view = new AuthorDTO();

        if(isWrite) {
            view.setId(entity.getId());
        }
        view.setName(entity.getName());

        return view;
    }

}
