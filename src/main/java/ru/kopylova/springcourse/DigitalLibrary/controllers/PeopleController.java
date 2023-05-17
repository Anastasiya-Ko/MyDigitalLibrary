package ru.kopylova.springcourse.DigitalLibrary.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kopylova.springcourse.DigitalLibrary.models.view.PersonDTO;
import ru.kopylova.springcourse.DigitalLibrary.services.PeopleService;
import ru.kopylova.springcourse.DigitalLibrary.util.page.sort.PersonSort;

@Validated
@RestController
@RequestMapping("/person")
public class PeopleController {

    PeopleService peopleService;


    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;

    }
    @PostMapping
    public PersonDTO createPerson(@Valid @RequestBody PersonDTO view) {
        return peopleService.createPerson(view);
    }

    @PutMapping(/*"/{id}"*/)
    public PersonDTO updatePerson(@Valid @RequestBody PersonDTO view) {
        return peopleService.updatePerson(view/*, id*/);
    }

    @GetMapping("/one/{id}")
    public PersonDTO readOnePersonById(@PathVariable Long id) {
        return peopleService.readOneById(id);
    }


    @GetMapping("/all")
    public Page<PersonDTO> readAllPeople(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "5") @Min(1) @Max(100) Integer limit,
            @RequestParam(value = "sort", defaultValue = "LASTNAME_ASC")PersonSort sort
            ) {
        return peopleService.readAllPeople(
                PageRequest.of(offset, limit, sort.getSortValue()));
    }

    @GetMapping("/by-name")
    public Page<PersonDTO> readOnePersonByLastName
            (@RequestParam("lastName") @Pattern(regexp = "[а-яёА-ЯЁ]+",
                    message = "Фамилия должна содержать только буквы русского алфавита") String lastName,
             Pageable pageable) {

        return peopleService.readByLastName(lastName, pageable);
    }

    @DeleteMapping("/{id}")
    public String deletePersonById(@PathVariable Long id) {
        return peopleService.deletePersonById(id);
    }

}
