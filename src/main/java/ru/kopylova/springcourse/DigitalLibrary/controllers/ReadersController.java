package ru.kopylova.springcourse.DigitalLibrary.controllers;

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
import ru.kopylova.springcourse.DigitalLibrary.models.view.ReaderDTORich;
import ru.kopylova.springcourse.DigitalLibrary.services.ReadersService;
import ru.kopylova.springcourse.DigitalLibrary.util.page.sort.ReaderSort;

@Validated
@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReadersController {

    ReadersService readersService;

    @PostMapping
    public ReaderDTORich createReader(@Valid @RequestBody ReaderDTORich view) {
        return readersService.createReader(view);
    }

    @PutMapping
    public ReaderDTORich updateReader(@Valid @RequestBody ReaderDTORich view) {
        return readersService.updateReader(view);
    }

    @GetMapping("/one/{id}")
    public ReaderDTORich readOneReaderById(@PathVariable @Min(0) Long id) {
        return readersService.readOneById(id);
    }


    @GetMapping("/all")
    public Page<ReaderDTORich> readAllReader(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "5") @Min(1) @Max(100) Integer limit,
            @RequestParam(value = "sort", defaultValue = "LASTNAME_ASC") ReaderSort sort
            ) {
        return readersService.readAllReader(
                PageRequest.of(offset, limit, sort.getSortValue()));
    }

    @GetMapping("/by-last-name")
    public Page<ReaderDTORich> readOneReaderByLastName
            (@RequestParam("last-name") @Pattern(regexp = "[а-яёА-ЯЁ]+",
                    message = "Фамилия должна содержать только буквы русского алфавита") String lastName,
             Pageable pageable) {

        return readersService.readByLastName(lastName, pageable);
    }

    @DeleteMapping("/{id}")
    public String deleteReaderById(@PathVariable Long id) {
        return readersService.deleteReaderById(id);
    }

}
