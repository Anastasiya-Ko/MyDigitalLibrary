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

    String title;

    String yearOfPublication;

    String authorId;

    String authorName;

    String readerId;

    String readerFirstName;

    String readerLastName;


}
