package ru.kopylova.springcourse.DigitalLibrary.orderBook.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTOEasy;
import ru.kopylova.springcourse.DigitalLibrary.books.models.view.BookDTORich;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
class OrderBookControllerTest {

    @Autowired
    OrderBookController controller;

    @Test
    void release() {

        String result = controller.release(1L);

        assertTrue(result.contains("Читатель вернул книгу") || result.contains("Она хранится в библиотеке"));
    }

    @Test
    void assign() {

        String result = controller.assign(1L, 42L);

        assertTrue(result.contains("Книга выдана читателю") || result.contains("она у другого читателя"));
    }

    @Test
    void readBooksFree() {

        List<BookDTOEasy> easyList = controller.readBooksFree();

        assertNotNull(easyList.stream().filter(book -> book.getTitle().equals("Хроники Реликта")));
    }

    @Test
    void readBooksBusy() {

        List<BookDTORich> easyList = controller.readBooksBusy();

        assertNotNull(easyList.stream().filter(book -> book.getTitle().equals("Поднятая целина")));
    }

    @Test
    void readBooksByReaderOwnerWhenReaderGotBook() {

        List<BookDTOEasy> easyList = controller.readBooksByReaderOwner(6L);

        assertFalse(easyList.isEmpty());

    }
}