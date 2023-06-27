package ru.kopylova.springcourse.DigitalLibrary.reports.reader.models.view;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReaderDTOReport {

    String id;

    /**
     * Имя читателя
     */
    String firstName;

    /**
     * Фамилия читателя
     */
    String lastName;

    /**
     * Возраст читателя
     */
    Integer age;
}
