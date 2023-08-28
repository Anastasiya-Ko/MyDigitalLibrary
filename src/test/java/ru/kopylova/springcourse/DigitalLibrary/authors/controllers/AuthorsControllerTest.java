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
    AuthorsController controller;

    AuthorDTO authorDTO = new AuthorDTO(2L, "Лев Толстой");

    @Test
    void createAuthorTest() {

        AuthorDTO test = controller.createAuthor(authorDTO);

        assertEquals("Лев Толстой", test.getName());
    }

    @Test
    void updateAuthorTest() {

        AuthorDTO test = controller.updateAuthor(authorDTO);

        assertEquals("Лев Толстой", test.getName());
    }

    @Test
    void readAllAuthorsTest() {

        Page<AuthorDTO> dtoPage = controller.readAllAuthors(Pageable.unpaged());
        List<AuthorDTO> dtoList = dtoPage.stream().toList();

        assertFalse(dtoPage.isEmpty());

        assertTrue(dtoList.contains(authorDTO));

    }

    @Test
    void readAuthorByIdTest() {

        Long authorId = 2L;

        AuthorDTO authorDTO = controller.readAuthorById(authorId);

        assertEquals("Лев Толстой", authorDTO.getName());
    }

    //так себе тест.. работает, в случае если доподлинно известно, что авторы без книг в библиотеке есть
//    @Test
//    void readAuthorHasNoBooksTestWhenAuthorIsNotEmpty() {
//
//        List<AuthorDTO> dtoList = controller.readAuthorHasNoBooks();
//
//        assertNotNull(dtoList);
//
//    }

    @Test
    void deleteAuthorById() {

        Long authorId = 2L;
        String result = controller.deleteAuthorById(authorId);

        assertNotNull(result);
    }
}