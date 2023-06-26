package ru.kopylova.springcourse.DigitalLibrary.mail.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.view.ReaderDTORich;
import ru.kopylova.springcourse.DigitalLibrary.readers.service.ReadersService;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class EmailService {

    JavaMailSender emailSender;
    ReadersService readersService;

    //    public void sendSimpleMessage(String to, String subject, String text) {
//
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setText(
//                    "This is the test email template for your email:\n%s\n");
//
//            message.setFrom("kopylova534512@yandex.ru");
//            message.setTo(to);
//            message.setSubject(subject);
//            message.setText(text);
//            emailSender.send(message);
//
//        } catch (MailException e) {
//            e.printStackTrace();
//        }
//    }
    public void sendSimpleMessage(Long readerId) {
        ReaderDTORich reader = readersService.readOneById(readerId);

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("kopylova534512@yandex.ru");
            message.setTo(reader.getEmail());
            message.setSubject("Возврат книги в библиотеку");
            message.setText(String.format("Уважаемый(ая) %s, напоминаем, что книгу нужно вернуть во время", reader.getFirstName()));

            emailSender.send(message);

        } catch (MailException e) {
            e.printStackTrace();
        }
    }

}
