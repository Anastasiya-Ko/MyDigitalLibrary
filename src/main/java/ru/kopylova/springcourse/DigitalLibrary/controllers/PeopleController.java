package ru.kopylova.springcourse.DigitalLibrary.controllers;

import org.springframework.web.bind.annotation.*;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Person;
import ru.kopylova.springcourse.DigitalLibrary.models.view.PersonDTO;
import ru.kopylova.springcourse.DigitalLibrary.services.PeopleService;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PeopleController {

    private final PeopleService peopleService;


    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;

    }

    @GetMapping("/all")
    public List<Person> getAll() {
        return peopleService.findAll();
    }


    @GetMapping("/one/{id}")
    public Person getOne(@PathVariable Long id) {
        return peopleService.findOneById(id);
    }

    @GetMapping("/by-name")
    public Person getByLastName(@RequestParam String lastName) {
        return peopleService.findByLastName(lastName);
    }
    @PostMapping
    public PersonDTO createPerson(@RequestBody PersonDTO view)
    {
        PersonDTO dto = peopleService.createPerson(view);
        return dto;
    }

    @PutMapping
    public PersonDTO updatePersonByLastName(@RequestBody PersonDTO view, @PathVariable String lastName) {
        PersonDTO dto = peopleService.updatePersonByLastName(view, lastName);
        return dto;
    }

    @DeleteMapping
    public void deletePersonBy(@PathVariable Long id) {
        peopleService.deletePersonById(id);
    }

}
