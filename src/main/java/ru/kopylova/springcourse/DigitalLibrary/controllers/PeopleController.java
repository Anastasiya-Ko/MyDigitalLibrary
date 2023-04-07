package ru.kopylova.springcourse.DigitalLibrary.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Person;
import ru.kopylova.springcourse.DigitalLibrary.models.view.PersonDTO;
import ru.kopylova.springcourse.DigitalLibrary.services.PeopleService;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PeopleController {

    //TODO: переделать возврат в методах на респонсЭнтити
    private final PeopleService peopleService;


    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;

    }

    @GetMapping("/all")
    public List<?> getAll(Pageable pageable, Sort sort) {
        return ResponseEntity.ok().body(peopleService.findAll(pageable, sort)).getBody();
    }


    @GetMapping("/one/{id}")
    public Person getOne(@PathVariable Long id) {
        return peopleService.findOneById(id);
    }

    //    @GetMapping("/by-name")
//    public ResponseEntity<?> getByLastName(@RequestParam("lastName") String lastName) {
//        return ResponseEntity.ok(peopleService.findByLastName(lastName));
//    }
    @GetMapping("/by-name")
    public List<Person> getByLastName(@RequestParam("lastName") String lastName) {
        return peopleService.findByLastName(lastName);
    }

    @PostMapping
    public PersonDTO createPerson(@Valid @RequestBody PersonDTO view) {
        return peopleService.createPerson(view);
    }

    @PutMapping
    public PersonDTO updatePersonByLastName(@Valid @RequestBody PersonDTO view, @PathVariable Long id) {
        return peopleService.updatePersonByLastName(view, id);
    }

    @DeleteMapping
    public void deletePersonBy(@PathVariable Long id) {
        peopleService.deletePersonById(id);
    }

}
