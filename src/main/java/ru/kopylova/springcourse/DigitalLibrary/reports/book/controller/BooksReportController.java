package ru.kopylova.springcourse.DigitalLibrary.reports.book.controller;


import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kopylova.springcourse.DigitalLibrary.reports.book.service.BookReportService;

import java.io.IOException;


@RestController
@RequestMapping("/book-report")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BooksReportController {

    BookReportService bookReportService;

    @GetMapping("/create-xlsx")
    public void createReportAllBooks(HttpServletResponse response) throws IOException {


        byte[] bytes = bookReportService.createReportAllBooks();

        response.setContentType("application/octet-stream");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = allBooks.xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
        response.setCharacterEncoding("UTF-8");
    }

}
