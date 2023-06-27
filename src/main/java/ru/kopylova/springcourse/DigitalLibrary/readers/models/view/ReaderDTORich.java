package ru.kopylova.springcourse.DigitalLibrary.readers.models.view;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kopylova.springcourse.DigitalLibrary.dictionary.Gender;
import ru.kopylova.springcourse.DigitalLibrary.util.valid.capitalLetter.CapitalLetter;
import ru.kopylova.springcourse.DigitalLibrary.util.valid.gender.GenderNamePattern;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReaderDTORich {

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
    @CapitalLetter()
    String lastName;

    @Schema(name = "Половая принадлежность читателя", example = "Муж/Жен")
    @NotNull(message = "Поле gender должно быть заполнено!")
    @GenderNamePattern(enumClass = Gender.class)
    @CapitalLetter
    String gender;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Integer age;

    @Schema(name = "Дата рождения читателя")
    @PastOrPresent(message = "Дата рождения должна содержать прошедшую дату или сегодняшнее число")
    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    LocalDate birthday;

    @Schema(name = "Электронная почта читателя", example = "lalala@mail.ru")
    @Email
    String email;

}
