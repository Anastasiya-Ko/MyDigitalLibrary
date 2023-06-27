package ru.kopylova.springcourse.DigitalLibrary.reports.book.models.view;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDTOReport {

    String bookId;

    /**
     * Название книги
     */
    String title;

    /**
     * Год публикации книги
     */
    String yearOfPublication;

    /**
     * Автор книги
     */
    String authorName;

    /**
     * Читатель книги в данный момент
     */
    String readerName;

}
