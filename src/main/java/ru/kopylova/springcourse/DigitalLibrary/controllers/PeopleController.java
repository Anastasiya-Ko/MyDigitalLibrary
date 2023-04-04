package ru.kopylova.springcourse.DigitalLibrary.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Person;
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
        return peopleService.findOne(id);
    }

//    @PostMapping
//    public PersonDTO create(@RequestBody PersonDTO view)
//    {
//        PersonDTO dto = peopleService.create(view);
//        return dto;
//    }
}
