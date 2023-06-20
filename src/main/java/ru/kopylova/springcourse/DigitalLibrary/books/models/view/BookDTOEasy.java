package ru.kopylova.springcourse.DigitalLibrary.books.models.view;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kopylova.springcourse.DigitalLibrary.authors.models.view.AuthorDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDTOEasy {

    Long id;

    @NotBlank(message = "У книги должно быть название!")
    @Size(min = 1, max = 100, message = "Книга должна иметь название от 1 до 50 букв")
    String title;

    //TODO как валидировать стринг, как-будто это дата :) чтобы невозможно было добавть книгу, написанную в будущем?
    @NotBlank(message = "У книги должен быть год издания")
    String yearOfPublication;

    @NotNull(message = "У книги должен быть автор, выберите из таблицы Автор")
    List<AuthorDTO> authorsOwner;

}

