package ru.kopylova.springcourse.DigitalLibrary.readers.controllers;

import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.view.ReaderDTORich;
import ru.kopylova.springcourse.DigitalLibrary.readers.service.ReadersService;
import ru.kopylova.springcourse.DigitalLibrary.reports.reader.service.ReaderReportService;
import ru.kopylova.springcourse.DigitalLibrary.util.page.sort.ReaderSort;

import java.io.IOException;

@Validated
@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReadersController {

    ReadersService readersService;
    ReaderReportService readerReportService;

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
            @RequestParam(value = "offset") @Min(0) Integer offset,
            @RequestParam(value = "limit") @Min(1) @Max(100) Integer limit,
            @RequestParam(value = "sort") ReaderSort sort
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

    @GetMapping("/create-xlsx")
    public void createReportAllBooks(HttpServletResponse response) throws IOException {


        byte[] bytes = readerReportService.createReportReaderGroupAge();

        response.setContentType("application/octet-stream");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = readersGroupAge.xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
        response.setCharacterEncoding("UTF-8");
    }

}
