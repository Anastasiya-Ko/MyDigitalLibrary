package ru.kopylova.springcourse.DigitalLibrary.authors.models.view;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kopylova.springcourse.DigitalLibrary.util.valid.CapitalLetter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorDTO {

    Long id;
    
    @NotEmpty(message = "У книги должен быть автор!")
    @CapitalLetter
    String name;

}
