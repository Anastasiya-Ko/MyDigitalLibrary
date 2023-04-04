package ru.kopylova.springcourse.DigitalLibrary.models.view;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Person;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDTO {

    @Schema(description = "Идентификатор человека", accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    Person owner;

    @NotEmpty(message = "У книги должно быть название!")
    @Size(min = 1, max = 100, message = "Книга должна иметь название от 1 до 100 букв")
    String name;


    @NotEmpty(message = "У книги должен быть год издания")
    @Pattern(message = "Год выпуска книги должен содержать 4 цифры",
            regexp = "^\\d{4}$")
    int yearOfPublication;

}
