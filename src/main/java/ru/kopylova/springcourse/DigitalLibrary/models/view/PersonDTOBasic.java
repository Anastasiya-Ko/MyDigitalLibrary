package ru.kopylova.springcourse.DigitalLibrary.models.view;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Book;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonDTOBasic {
    Long id;

    @NotEmpty(message = "У человека должно быть имя!")
    @Pattern(regexp = "[а-яёА-ЯЁ]+", message = "Имя должно содержать только буквы русского алфавита")
    String firstName;


    @NotEmpty(message = "У человека должна быть фамилия!")
    @Pattern(regexp = "[а-яёА-ЯЁ]+", message = "Фамилия должна содержать только буквы русского алфавита")
    String lastName;

    List<Book> books;
}
