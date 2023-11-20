package ru.kopylova.springcourse.DigitalLibrary.mail.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.view.ReaderDTORich;
import ru.kopylova.springcourse.DigitalLibrary.readers.service.ReadersService;

/**
 * Сервис для отправки электронных писем читателям
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class EmailService {

    /**
     * Подгружаем класс, позволяющий отправлять сообщения из приложения спринг бут
     */

    JavaMailSender emailSender;

    ReadersService readersService;

    /**
     * Метод для отправки простого сообщения (без вложений) читателю, о необходимости вернуть книгу в библиотеку
     *
     * @param readerId идентификатор читателя, которому необходимо отправить сообщение
     */
    public String sendSimpleMessage(Long readerId) {

        ReaderDTORich reader = readersService.readOneById(readerId);


        SimpleMailMessage message = new SimpleMailMessage();


        message.setFrom("kopylova534512@yandex.ru");
        message.setTo(reader.getEmail());
        message.setSubject("Возврат книги в библиотеку");
        message.setText(String.format("Уважаемый(ая) %s, напоминаем, что книгу нужно вернуть во время!", reader.getFirstName()));

        emailSender.send(message);

        return String.format("Сообщение, читателю %s %s, успешно отправлено", reader.getFirstName(), reader.getLastName());
    }

}
