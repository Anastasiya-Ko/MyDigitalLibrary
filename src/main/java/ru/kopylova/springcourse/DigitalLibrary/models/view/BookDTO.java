package ru.kopylova.springcourse.DigitalLibrary.models.view;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Author;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Person;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDTO {

    Person personOwner;

    Author authorOwner;

    @NotEmpty(message = "У книги должно быть название!")
    @Size(min = 1, max = 100, message = "Книга должна иметь название от 1 до 50 букв")
    String name;


    @NotEmpty(message = "У книги должен быть год издания")
    @PastOrPresent(message = "Дата издания книги должна содержать прошедшую дату или сегодняшнее число")
    LocalDate yearOfPublication;

}
