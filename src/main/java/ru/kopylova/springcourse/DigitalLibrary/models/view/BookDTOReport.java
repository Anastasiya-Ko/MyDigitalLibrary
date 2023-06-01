package ru.kopylova.springcourse.DigitalLibrary.models.view;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDTOReport {

    Long bookId;

    String title;

    LocalDate yearOfPublication;

    Long authorId;

    String authorName;

    String readerId;

    String readerFirstName;

    String readerLastName;


}
