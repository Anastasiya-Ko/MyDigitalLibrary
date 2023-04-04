package ru.kopylova.springcourse.DigitalLibrary.models.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Gender;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonDTO {

    @Schema(description = "Идентификатор человека", accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @NotEmpty(message = "У человека должно быть имя!")
    @Size(min = 1, max = 100, message = "Имя должно быть длиной от 1 до 100 букв")
    String firstName;


    @NotEmpty(message = "У человека должна быть фамилия!")
    @Size(min = 1, max = 100, message = "Имя должно быть длиной от 1 до 100 букв")
    String lastName;


    @NotEmpty(message = "У человека должен быть год рождения!")
    @PastOrPresent(message = "Год рождения должен содержать прошедшую дату или сегодняшнее число")
    @Pattern(message = "Год рождения должен содержать 4 цифры",
            regexp = "^[12][09][0-9][0-9]$")
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalTime birthday;

    @Email
    String email;

    @NotEmpty(message = "Поле должно быть заполнено! MAN - для мужчины, WOMAN - для женщины")
    Gender gender;

}
