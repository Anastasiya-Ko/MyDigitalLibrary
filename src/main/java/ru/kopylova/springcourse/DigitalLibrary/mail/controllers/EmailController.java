package ru.kopylova.springcourse.DigitalLibrary.mail.controllers;

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
public class EmailController {
    EmailService emailService;

    @GetMapping("/{readerId}")
    public void sendEmail(@PathVariable Long readerId){
        emailService.sendSimpleMessage(readerId);
    }
}
