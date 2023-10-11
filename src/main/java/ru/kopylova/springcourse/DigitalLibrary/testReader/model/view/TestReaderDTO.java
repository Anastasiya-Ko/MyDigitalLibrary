package ru.kopylova.springcourse.DigitalLibrary.testReader.model.view;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(name = "Сокращённое представление тестового читателя")
public class TestReaderDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    /**
     * Имя тестового читателя
     */
    @Schema(name = "Имя тестового читателя")
    @NotBlank
    String name;

    /**
     * Фамилия тестового читателя
     */
    @Schema(name = "Фамилия тестового читателя")
    @NotBlank
    String lastName;

}
