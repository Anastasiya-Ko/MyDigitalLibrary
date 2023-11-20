package ru.kopylova.springcourse.DigitalLibrary.mail;

public interface MailInterface {

    /**
     * Метод для отправки простого сообщения (без вложений) читателю, о необходимости вернуть книгу в библиотеку
     * @param readerId идентификатор читателя, которому необходимо отправить сообщение
     */
    String sendSimpleMessage(Long readerId);
}
