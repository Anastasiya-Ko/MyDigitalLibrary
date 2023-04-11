package ru.kopylova.springcourse.DigitalLibrary.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kopylova.springcourse.DigitalLibrary.models.view.PersonDTO;
import ru.kopylova.springcourse.DigitalLibrary.services.PeopleService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/person")
public class PeopleController {

    private final PeopleService peopleService;


    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;

    }

    @PostMapping
    public PersonDTO createPerson(@Valid @RequestBody PersonDTO view) {
        return peopleService.createPerson(view);
    }

    @GetMapping("/all")
    public List<PersonDTO> getAllPeople(Pageable pageable) {
        return ResponseEntity.ok().body(peopleService.readAllPeople(pageable)).getBody();
    }

    @GetMapping("/one/{id}")
    public PersonDTO getPersonById(@PathVariable Long id) {
        return peopleService.findOneById(id);
    }

    @GetMapping("/by-name")
    public List<PersonDTO> getPeopleByLastName
            (@Pattern(regexp = "[а-яёА-ЯЁ]+", message = "Фамилия должна содержать только буквы русского алфавита")
             @RequestParam("lastName") String lastName) {
        return peopleService.findByLastName(lastName);
    }

    @PutMapping
    public PersonDTO updatePersonByLastName(@Valid @RequestBody PersonDTO view) {
        return peopleService.updatePersonById(view);
    }

    @DeleteMapping("/{id}")
    public String deletePersonById(@PathVariable Long id) {
        return peopleService.deletePersonById(id);
    }

}
