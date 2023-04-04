package ru.kopylova.springcourse.DigitalLibrary.models.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Book;

import java.util.List;

public class AuthorDTO {

    @Schema(description = "Идентификатор автора", accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @NotEmpty(message = "У книги должен быть автор!")
    String name;

}
