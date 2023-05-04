package ru.kopylova.springcourse.DigitalLibrary.models.view;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Book;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorDTO {
    
    @NotEmpty(message = "У книги должен быть автор!")
    String name;

    List<Book> books;

}
