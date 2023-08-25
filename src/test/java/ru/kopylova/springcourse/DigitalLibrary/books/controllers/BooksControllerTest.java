package ru.kopylova.springcourse.DigitalLibrary.books.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.kopylova.springcourse.DigitalLibrary.authors.models.view.AuthorDTO;
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTOEasy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
class BooksControllerTest {

    @Autowired
    BooksController controller;
    @Test
    void createBookTest() {

        BookDTOEasy bookDTOEasy = new BookDTOEasy();
        bookDTOEasy.setTitle("test");
        bookDTOEasy.setYearOfPublication(2000);
        bookDTOEasy.setAuthorsOwner(new ArrayList<>());

        BookDTOEasy dtoEasy = controller.createBook(bookDTOEasy);

        assertEquals(dtoEasy.getYearOfPublication(), 2000);
    }

    @Test
    void updateBookTest() {

        BookDTOEasy bookDTOEasy = new BookDTOEasy();

        List<AuthorDTO> authorDTOList = new ArrayList<>();
        authorDTOList.add(new AuthorDTO(13L, ""));

        bookDTOEasy.setId(85L);
        bookDTOEasy.setTitle("test");
        bookDTOEasy.setYearOfPublication(2000);
        bookDTOEasy.setAuthorsOwner(authorDTOList);

        assertEquals(bookDTOEasy.getYearOfPublication(), 2000);

    }

    @Test
    void readAllBooksTest() {
    }

    @Test
    void readOneBookByIdTest() {
    }

    @Test
    void readBooksByNameStartingWithTest() {
    }

    @Test
    void readBooksWriteGroupAuthorsTest() {
    }

    @Test
    void readBooksWriteRequestAuthorTest() {
    }

    @Test
    void deleteBookByIdTest() {
    }
}