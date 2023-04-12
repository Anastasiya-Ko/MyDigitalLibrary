package ru.kopylova.springcourse.DigitalLibrary.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Person;
import ru.kopylova.springcourse.DigitalLibrary.models.view.PersonDTO;
import ru.kopylova.springcourse.DigitalLibrary.services.PeopleService;
import ru.kopylova.springcourse.DigitalLibrary.util.page.sort.PersonSort;

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

    @GetMapping("/one/{id}")
    public PersonDTO readOnePersonById(@PathVariable Long id) {
        return peopleService.readOneById(id);
    }


    @GetMapping("/all")
    public Page<PersonDTO> readAllPeople(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "5") @Min(1) @Max(100) Integer limit,
            @RequestParam("sort")PersonSort sort
            ) {
        return peopleService.readAllPeople(
                PageRequest.of(offset, limit, sort.getSortValue()));
    }

    @GetMapping("/by-name")
    public Page<PersonDTO> readOnePersonByLastName
            (@RequestParam("lastName") @Pattern(regexp = "[а-яёА-ЯЁ]+",
                    message = "Фамилия должна содержать только буквы русского алфавита") String lastName,
             @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
             @RequestParam(value = "limit", defaultValue = "5") @Min(1) @Max(100) Integer limit) {

        return peopleService.readByLastName(lastName,
                PageRequest.of(offset, limit));
    }

    @PutMapping("/{id}")
    public PersonDTO updatePerson(@Valid @RequestBody PersonDTO view,
                                  @PathVariable Long id) {
        return peopleService.updatePerson(view, id);
    }

    @DeleteMapping("/{id}")
    public String deletePersonById(@PathVariable Long id) {
        return peopleService.deletePersonById(id);
    }

}
