package ru.kopylova.springcourse.DigitalLibrary.books.controllers;

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
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTOEasy;
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTORich;
import ru.kopylova.springcourse.DigitalLibrary.dictionary.BookSort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
class BooksControllerTest {

    @Autowired
    BooksController controller;

    BookDTOEasy bookDTOEasy = new BookDTOEasy(85L, "test", new ArrayList<>(), 2000);

    @Test
    void createBookTest() {

        BookDTOEasy dtoEasy = controller.createBook(bookDTOEasy);

        assertEquals(dtoEasy.getYearOfPublication(), 2000);
    }

    @Test
    void updateBookTest() {

        BookDTOEasy dtoEasy = controller.updateBook(bookDTOEasy);

        assertEquals(dtoEasy.getYearOfPublication(), 2000);

    }

    @Test
    void readAllBooksTest() {

        var test = controller.createBook(bookDTOEasy);

        Page<BookDTOEasy> easyPage = controller.readAllBooks(0, 5, BookSort.ID_DESC);

        assertFalse(easyPage.isEmpty());
        assertEquals(easyPage.getContent().get(0).getTitle(), test.getTitle());

    }

    @Test
    void readOneBookByIdTest() {

        BookDTORich bookDTORich = controller.readOneBookById(90L);

        assertEquals(bookDTORich.getTitle(), "Трудно быть богом");

    }

    @Test
    void readBooksByNameStartingWithTest() {

        var test = controller.createBook(bookDTOEasy);

        Page<BookDTOEasy> easyPage = controller.readBooksByNameStartingWith("te", Pageable.unpaged());

        assertEquals(easyPage.getContent().get(0).getTitle(), test.getTitle());

    }

    @Test
    void readBooksWriteGroupAuthorsTest() {

        List<BookDTOEasy> easyList = controller.readBooksWriteGroupAuthors();

        assertNotNull(easyList.stream()
                .filter(book -> "Трудно быть богом".equals(book.getTitle()))
                .findAny());

    }

    @Test
    void readBooksWriteRequestAuthorTest() {

        List<BookDTOEasy> easyList = controller.readBooksWriteRequestAuthor(13L);

        assertFalse(easyList.isEmpty());

        assertNotNull(easyList.stream()
                .filter(book -> "Жажда власти 2".equals(book.getTitle()))
                .findAny());

    }

    @Test
    void deleteBookByIdTest() {

        String result = controller.deleteBookById(90L);

        assertNotNull(result);
    }
}