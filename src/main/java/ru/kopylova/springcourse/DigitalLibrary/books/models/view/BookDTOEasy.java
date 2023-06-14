package ru.kopylova.springcourse.DigitalLibrary.books.models.view;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kopylova.springcourse.DigitalLibrary.authors.models.view.AuthorDTO;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDTOEasy {

    Long id;

    @NotEmpty(message = "У книги должно быть название!")
    @Size(min = 1, max = 100, message = "Книга должна иметь название от 1 до 50 букв")
    String title;

    @NotNull(message = "У книги должен быть год издания")
    @PastOrPresent(message = "Дата издания книги должна содержать прошедшую дату или сегодняшнее число")
    LocalDate yearOfPublication;

    @NotNull(message = "У книги должен быть автор, выберите из таблицы Автор")
    List<AuthorDTO> authorsOwner;

}

