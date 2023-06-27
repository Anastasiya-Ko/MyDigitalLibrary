package ru.kopylova.springcourse.DigitalLibrary.readers.models.view;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kopylova.springcourse.DigitalLibrary.util.valid.capitalLetter.CapitalLetter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(name = "Сокращённое представление читателя")
public class ReaderDTOEasy {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @Schema(name = "Имя читателя", description = "на русском языке, с заглавной буквы")
    @NotBlank(message = "У человека должно быть имя!")
    @Pattern(regexp = "[а-яёА-ЯЁ]+", message = "Имя должно содержать только буквы русского алфавита")
    @CapitalLetter
    String firstName;


    @Schema(name = "Фамилия читателя", description = "на русском языке, с заглавной буквы")
    @NotBlank(message = "У человека должна быть фамилия!")
    @Pattern(regexp = "[а-яёА-ЯЁ]+", message = "Фамилия должна содержать только буквы русского алфавита")
    @CapitalLetter
    String lastName;

}

