package ru.kopylova.springcourse.DigitalLibrary.testReader.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kopylova.springcourse.DigitalLibrary.testReader.model.view.TestReaderDTO;
import ru.kopylova.springcourse.DigitalLibrary.testReader.service.TestReaderService;

import java.util.List;

@RestController
@RequestMapping("/test-reader")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Контроллер Тестовых Читателей")
public class TestReaderController {

    TestReaderService testReaderService;

    @Operation(summary = "вывод информации о всех тестовых читателях")
    @GetMapping("/all")
    public List<TestReaderDTO> readAllTestReaders() {
        return testReaderService.readAllTestReaders();
    }

}
