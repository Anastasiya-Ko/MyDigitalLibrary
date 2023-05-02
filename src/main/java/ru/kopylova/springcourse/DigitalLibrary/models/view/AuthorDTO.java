package ru.kopylova.springcourse.DigitalLibrary.models.view;

import jakarta.validation.constraints.NotEmpty;

public class AuthorDTO {
    @NotEmpty(message = "У книги должен быть автор!")
    String name;

}
