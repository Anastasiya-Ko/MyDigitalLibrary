package ru.kopylova.springcourse.DigitalLibrary.readers.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kopylova.springcourse.DigitalLibrary.dictionary.ReaderSort;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.view.ReaderDTORich;
import ru.kopylova.springcourse.DigitalLibrary.readers.service.ReadersService;


@Validated
@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Контроллер Читатели", description = "Взаимодействие со справочником читателей")
public class ReadersController {

    ReadersService readersService;

    @PostMapping
    @Operation(summary = "Внесение нового читателя в бд")
    public ReaderDTORich createReader(@Valid @RequestBody ReaderDTORich view) {
        return readersService.createReader(view);
    }

    @PutMapping
    @Operation(summary = "Обновление существующего читателя в бд")
    public ReaderDTORich updateReader(@Valid @RequestBody ReaderDTORich view) {
        return readersService.updateReader(view);
    }

    @GetMapping("/one/{readerId}")
    @Operation(summary = "Чтение информации о читателе по его id")
    public ReaderDTORich readOneReaderById(@PathVariable @Parameter(description = "id читателя должен быть положительным числом") @Min(0) Long readerId) {
        return readersService.readOneById(readerId);
    }


    @GetMapping("/all")
    @Operation(summary = "Постраничный вывод информации о всех читателях")
    public Page<ReaderDTORich> readAllReader(
            @RequestParam(value = "offset")
            @Parameter(description = "Страница")
            @Min(0) Integer offset,
            @RequestParam(value = "limit")
            @Parameter(description = "Количество читателей на странице")
            @Min(1)
            @Max(100) Integer limit,
            @RequestParam(value = "sort")
            @Parameter(description = "Возможная сортировка") ReaderSort sort

    ) {
        return readersService.readAllReader(PageRequest.of(offset, limit, sort.getSortValue()));
    }

    @GetMapping("/by-last-name")
    @Operation(summary = "Отображение читателя по его фамилии")
    public Page<ReaderDTORich> readOneReaderByLastName(
            @RequestParam("last-name")
            @Pattern(regexp = "[а-яёА-ЯЁ]+", message = "Фамилия должна содержать только буквы русского алфавита")
            String lastName, Pageable pageable) {

        return readersService.readByLastName(lastName, pageable);
    }

    @DeleteMapping("/{readerId}")
    @Operation(summary = "Удаление читателя по его id")
    public String deleteReaderById(@PathVariable Long readerId) {
        return readersService.deleteReaderById(readerId);
    }

}
