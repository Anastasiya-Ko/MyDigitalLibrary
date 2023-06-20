package ru.kopylova.springcourse.DigitalLibrary.authors.models.view;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kopylova.springcourse.DigitalLibrary.util.valid.capitalLetter.CapitalLetter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorDTO {

    Long id;
    
    @NotBlank(message = "У книги должен быть автор!")
    @CapitalLetter
    String name;

}
