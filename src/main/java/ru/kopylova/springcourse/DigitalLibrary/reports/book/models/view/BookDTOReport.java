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

    String authorName;

    String readerFirstName;

    String readerLastName;


}
