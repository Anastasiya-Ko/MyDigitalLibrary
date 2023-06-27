package ru.kopylova.springcourse.DigitalLibrary.authors.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.kopylova.springcourse.DigitalLibrary.authors.mapper.AuthorMapper;
import ru.kopylova.springcourse.DigitalLibrary.authors.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.authors.models.view.AuthorDTO;
import ru.kopylova.springcourse.DigitalLibrary.authors.repository.AuthorsRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с Автором
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorService {

    /**
     * Подгрузка репозитория автора
     */
    AuthorsRepository authorsRepository;

    /**
     * Маппинг автора
     */
    AuthorMapper authorMapper;

    /**
     * Занесение нового автора в бд
     * @param view представление автора для внесения в бд
     * @return представление, сохранённой в бд, сущности
     */
    public AuthorDTO createAuthor(AuthorDTO view) {
        Author entity = authorMapper.mapperToEntity(view, false);
        authorsRepository.save(entity);
        return authorMapper.mapperToDTO(entity, true);
    }

    /**
     * Обновление автора в бд
     * @param view представление автора для изменения в бд
     * @return представление, изменённой в бд, сущности
     */
    public AuthorDTO updateAuthor(AuthorDTO view) {
        var updateEntity = getById(view.getId());
        updateEntity = authorMapper.mapperToEntity(view, true);
        authorsRepository.save(updateEntity);
        return authorMapper.mapperToDTO(updateEntity, true);
    }

    /**
     * Поиск в бд автора по authorId
     * @param authorId идентификатор искомого автора
     * @return представление, найденной в бд сущности, автора
     */
    public AuthorDTO readOneById(Long authorId) {
        return authorMapper.mapperToDTO(getById(authorId), true);
    }

    /**
     * Постраничный вывод справочника авторов
     * @return страница с дто авторов
     */
    public Page<AuthorDTO> readAll(Pageable pageable) {
        Page<Author> entityPage = authorsRepository.findAll(pageable);
        return entityPage.map(entity -> authorMapper.mapperToDTO(entity, true));
    }

    /**
     * Чтение авторов, книги которых не представлены в библиотеке
     * @return лист представлений авторов
     */
    public List<AuthorDTO> readAuthorHasNoBooks() {
        List<Author> entityList = authorsRepository.findAuthorHasNoBooks();
        return entityList.stream()
                .map(entity -> authorMapper.mapperToDTO(entity, true))
                .collect(Collectors.toList());
    }

    /**
     * Удаление автора
     * @param authorId идентификатор удаляемого автора
     * @return Информационное сообщение
     */
    public String deleteAuthorById(Long authorId) {
        Author entity = getById(authorId);
        authorsRepository.deleteById(authorId);
        return String.format("Автор с именем \"%s\" успешно удалён", entity.getName());
    }


    /**
     * Метод внутреннего пользования, для получения автора(сущности) по идентификатору
     */
    private Author getById(Long authorId) {
        String ex = String.format(("Автор с номером = %d не найден"), authorId);
        return authorsRepository.findById(authorId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, ex));
    }
}
