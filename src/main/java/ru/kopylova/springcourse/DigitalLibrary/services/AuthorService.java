package ru.kopylova.springcourse.DigitalLibrary.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.kopylova.springcourse.DigitalLibrary.mappers.AuthorMapper;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.models.view.AuthorDTO;
import ru.kopylova.springcourse.DigitalLibrary.repositories.AuthorsRepository;

@Setter
@Getter
@Service
public class AuthorService {

    private final AuthorsRepository authorsRepository;

    @Autowired
    AuthorMapper authorMapper;

    public AuthorService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    public AuthorDTO createAuthor(AuthorDTO view) {
        Author entity = authorMapper.mapperToEntity(view, false);
        authorsRepository.save(entity);
        return authorMapper.mapperToDTO(entity, true);
    }

    //TODO нужно подумать о надобности входящего id
    public AuthorDTO updateAuthor(AuthorDTO viewRequest, Long id) {
        getById(id);
        Author entity = authorMapper.mapperToEntity(viewRequest, false);
        entity.setId(id);
        authorsRepository.save(entity);
        return authorMapper.mapperToDTO(entity, true);
    }

    public AuthorDTO readOneById(Long id) {
        return authorMapper.mapperToDTO(getById(id), true);
    }

    public Page<AuthorDTO> readAll(Pageable pageable) {
        Page<Author> entityPage = authorsRepository.findAll(pageable);
        return entityPage.map(entity -> authorMapper.mapperToDTO(entity, true));
    }

    public String deleteAuthorById(Long id) {
        getById(id);
        authorsRepository.deleteById(id);
        return String.format("Автор с id=%d успешно удалён", id);
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
