package ru.kopylova.springcourse.DigitalLibrary.authors.models.view;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kopylova.springcourse.DigitalLibrary.util.valid.capitalLetter.CapitalLetter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Представление автора")
public class AuthorDTO {

    @Schema(description = "Идентификатор", accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @Schema(name = "Имя и Фамилия автора", description = "должно быть с заглавной буквы")
    @NotBlank(message = "У книги должен быть автор!")
    @CapitalLetter
    String name;

}
