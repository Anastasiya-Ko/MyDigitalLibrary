package ru.kopylova.springcourse.DigitalLibrary.reports.reader.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kopylova.springcourse.DigitalLibrary.reports.reader.service.ReaderReportService;

import java.io.IOException;

@RestController
@RequestMapping("/reader-report")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Контроллер для создания отчёта excel")
public class ReadersReportController {

    ReaderReportService readerReportService;
    @GetMapping("/create-xlsx")
    @Operation(description = "Метод создания отчёта по возрастным группам читателей в библиотеке, формат - excel")
    public void createReportReadersGroup(HttpServletResponse response) throws IOException {

        byte[] bytes = readerReportService.createReportReaderGroupAge();

        response.setContentType("application/octet-stream");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = readersGroupAge.xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
        response.setCharacterEncoding("UTF-8");
    }
}
