package ru.kopylova.springcourse.DigitalLibrary.mail.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kopylova.springcourse.DigitalLibrary.mail.service.EmailService;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Контроллер для отправки сообщений читателю")
public class EmailController {

    EmailService emailService;

    @GetMapping("/{readerId}")
    @Operation(summary = "Отправка сообщения читателю", description = "по идентификатору читателя")
    public void sendEmail(@PathVariable Long readerId){
        emailService.sendSimpleMessage(readerId);
    }
}
