package ru.kopylova.springcourse.DigitalLibrary.readers.models.view;


import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.entity.Gender;
import ru.kopylova.springcourse.DigitalLibrary.util.valid.capitalLetter.CapitalLetter;
import ru.kopylova.springcourse.DigitalLibrary.util.valid.genger.GenderNamePattern;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReaderDTORich {

    Long id;

    @NotBlank(message = "У человека должно быть имя!")
    @Pattern(regexp = "[а-яёА-ЯЁ]+", message = "Имя должно содержать только буквы русского алфавита")
    @CapitalLetter
    String firstName;


    @NotBlank(message = "У человека должна быть фамилия!")
    @Pattern(regexp = "[а-яёА-ЯЁ]+", message = "Фамилия должна содержать только буквы русского алфавита")
    @CapitalLetter()
    String lastName;

    @NotNull(message = "Поле gender должно быть заполнено!")
    @GenderNamePattern(enumClass = Gender.class)
    @CapitalLetter
    String gender;

    Integer age;

    @PastOrPresent(message = "Дата рождения должна содержать прошедшую дату или сегодняшнее число")
    LocalDate birthday;

    @Email
    String email;

}
