package ru.kopylova.springcourse.DigitalLibrary.books.models.view;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
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
@Schema(name = "Укороченное представление книги", description = "Основная информация о книге")
public class BookDTOEasy {

    @Schema(description = "Идентификатор", accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @Schema(name = "Название книги")
    @NotBlank(message = "У книги должно быть название!")
    @Size(min = 1, message = "Книга должна иметь название, состоящее минимум из одного символа")
    String title;

    @Schema(name = "Автор, написавший данную книгу", description = "Выбирается из справочника Автор")
    @NotNull(message = "У книги должен быть автор. Выберите его из справочника Автор")
    List<AuthorDTO> authorsOwner;

    @Schema(name = "Год издания книги")
    @NotNull(message = "У книги должен быть год издания")
    @Min(value = 1377,
         message = "Самая древняя книга, сохранившаяся до наших времён, напечатана в 1377 году. Введите год после этой даты")
    Integer yearOfPublication;

}

