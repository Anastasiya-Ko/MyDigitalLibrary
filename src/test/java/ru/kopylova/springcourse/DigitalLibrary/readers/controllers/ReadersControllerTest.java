package ru.kopylova.springcourse.DigitalLibrary.readers.controllers;

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
import ru.kopylova.springcourse.DigitalLibrary.dictionary.ReaderSort;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.view.ReaderDTORich;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
class ReadersControllerTest {

    @Autowired
    ReadersController controller;

    ReaderDTORich view =
            new ReaderDTORich(42L,
                    "Милка",
                    "Сладко",
                    "Жен",
                    26,
                    LocalDate.of(1997, 5, 31),
                    "sladkaya@mail.ru");


    @Test
    void createReaderTest() {

        ReaderDTORich readerDTORich = controller.createReader(view);

        assertEquals(readerDTORich.getFirstName(), "Милка");
    }

    @Test
    void updateReaderTest() {

        ReaderDTORich readerDTORich = controller.updateReader(view);

        assertEquals(readerDTORich.getFirstName(), "Милка");
    }

    @Test
    void readOneReaderByIdTest() {

        ReaderDTORich readerDTORich = controller.readOneReaderById(42L);

        assertEquals(readerDTORich.getFirstName(), "Милка");
    }

    @Test
    void readAllReaderTest() {

        Page<ReaderDTORich> richPage = controller.readAllReader(1, 20, ReaderSort.ID_DESC);

        assertFalse(richPage.isEmpty());
        assertNotNull(richPage.stream().filter(reader -> reader.getLastName().equals("Сладко")));

    }

    @Test
    void readOneReaderByLastNameTest() {

        Page<ReaderDTORich> richPage = controller.readOneReaderByLastName("Сладко", Pageable.unpaged());

        assertFalse(richPage.isEmpty());
        assertNotNull(richPage.stream().filter(reader -> reader.getLastName().equals("Сладко")));

    }

    @Test
    void deleteReaderByIdTest() {

        String result = controller.deleteReaderById(42L);

        assertTrue(result.contains("успешно удалён"));
        assertNotNull(result);
    }
}