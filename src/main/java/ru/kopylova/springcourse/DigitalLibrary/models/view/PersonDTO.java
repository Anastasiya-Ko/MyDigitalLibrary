package ru.kopylova.springcourse.DigitalLibrary.models.view;


import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Gender;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonDTO {

   // Long id;

    @NotEmpty(message = "У человека должно быть имя!")
    @Pattern(regexp = "[а-яёА-ЯЁ]+", message = "Имя должно содержать только буквы русского алфавита")
    String firstName;


    @NotEmpty(message = "У человека должна быть фамилия!")
    @Pattern(regexp = "[а-яёА-ЯЁ]+", message = "Фамилия должна содержать только буквы русского алфавита")
    String lastName;


    @NotNull(message = "У человека должна быть дата рождения!")
    @PastOrPresent(message = "Дата рождения должна содержать прошедшую дату или сегодняшнее число")
    LocalDate birthday;

    @Email
    String email;

    @NotNull(message = "Поле gender должно быть заполнено! Муж - для мужчины, Жен - для женщины")
    Gender gender;


    Integer age;
}
