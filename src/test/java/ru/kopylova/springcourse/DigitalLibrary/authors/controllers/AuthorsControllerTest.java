package ru.kopylova.springcourse.DigitalLibrary.authors.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.kopylova.springcourse.DigitalLibrary.authors.models.view.AuthorDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
class AuthorsControllerTest {

    @Autowired
    AuthorsController authorsController;

    @Test
    void createAuthorTest() {

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("test");

        AuthorDTO test = authorsController.createAuthor(authorDTO);
        assertEquals("test", test.getName());
    }

    @Test
    void updateAuthorTest() {

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(2L);
        authorDTO.setName("test");

        AuthorDTO test = authorsController.updateAuthor(authorDTO);
        assertEquals("test", test.getName());
    }

    @Test
    void readAllAuthorsTest() {

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("test");

        authorsController.createAuthor(authorDTO);

        Page<AuthorDTO> dtoPage = authorsController.readAllAuthors(Pageable.unpaged());
        assertFalse(dtoPage.isEmpty());
    }

    @Test
    void readAuthorByIdTest() {

        Long authorId = 2L;

        AuthorDTO authorDTO = authorsController.readAuthorById(authorId);

        assertEquals("Лев Толстой", authorDTO.getName());
    }

    //так себе тест.. работает, в случае если доподлинно известно, что авторы без книг в библиотеке есть
    @Test
    void readAuthorHasNoBooksTestWhenAuthorIsNotEmpty() {

        List<AuthorDTO> dtoList = authorsController.readAuthorHasNoBooks();

        assertNotNull(dtoList);

    }

    @Test
    void deleteAuthorById() {

        Long authorId = 2L;
        String result = authorsController.deleteAuthorById(authorId);

        assertNotNull(result);
    }
}