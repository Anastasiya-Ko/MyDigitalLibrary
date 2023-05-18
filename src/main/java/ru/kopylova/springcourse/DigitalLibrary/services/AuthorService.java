package ru.kopylova.springcourse.DigitalLibrary.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.models.view.AuthorDTO;
import ru.kopylova.springcourse.DigitalLibrary.repositories.AuthorsRepository;

@Service
public class AuthorService {

    private final AuthorsRepository authorsRepository;

    public AuthorService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    public AuthorDTO readOneById(Long id) {
        return mapperToDTO(getById(id), true);
    }

    public Page<AuthorDTO> readAllBooks(Pageable pageable) {
        Page<Author> entityPage = authorsRepository.findAll(pageable);
        return entityPage.map(entity -> mapperToDTO(entity, false));
    }

    public Author mapperToEntity(AuthorDTO view, boolean isWrite) {
        Author entity = new Author();

        if(isWrite) {
            entity.setId(view.getId());
        }

        entity.setName(view.getName());
     //   entity.setBooks(view.getBooks());

        return entity;

    }

    public AuthorDTO mapperToDTO(Author entity, boolean isWrite) {

        AuthorDTO view = new AuthorDTO();

        if(isWrite) {
            view.setId(entity.getId());
        }
        view.setName(entity.getName());
    //    view.setBooks(entity.getBooks());

        return view;
    }

    /**
     * Метод внутреннего пользования, для получения автора(сущности) по идентификатору
     */
    private Author getById(Long id) {
        String ex = String.format(("Автор с ID = %d не найден"), id);
        return authorsRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, ex));
    }
}
