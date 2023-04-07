package ru.kopylova.springcourse.DigitalLibrary.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Person;
import ru.kopylova.springcourse.DigitalLibrary.models.view.PersonDTO;
import ru.kopylova.springcourse.DigitalLibrary.repositories.PeopleRepository;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PeopleService {

    final PeopleRepository peopleRepository;


    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    public List<Person> findAll(Pageable pageable, Sort sort) {
        return peopleRepository.findAll(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("lastName"))).getContent();
    }

    public Person findOneById(Long id) {

        String ex = String.format(("Пользователь с ID = %d не найден"), id);

       return peopleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
               HttpStatus.NOT_FOUND, ex));
    }

//    public ResponseEntity<List<?>> findByLastName(String lastName) {
//        String ex = String.format(("Пользователь с фамилией = %s не найден"), lastName);
//
//        List<Person> result = peopleRepository.findByLastName(lastName);
//
//        if (result == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        return ResponseEntity.ok(result);
//                //orElseThrow(() -> new ResponseStatusException(
//                //HttpStatus.NOT_FOUND, ex));
//    }

//    public List<Person> findByLastName(String lastName) {
//        String ex = String.format(("Пользователь с фамилией = %s не найден"), lastName);
//
//        List<Person> result = peopleRepository.findByLastName(lastName);
//
//        if (result.isEmpty()) {
//            return new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
//        }
//
//        return result;
//        //orElseThrow(() -> new ResponseStatusException(
//        //HttpStatus.NOT_FOUND, ex));
//    }

    public ResponseEntity<List<Person>> findByLastName(String lastName) {
        String ex = String.format(("Пользователь с фамилией = %s не найден"), lastName);

        return peopleRepository.findByLastNameOrderByAge(lastName).stream().findAny().orElse(new ResponseStatusException(HttpStatus.NOT_FOUND, ex));

    }

    public PersonDTO createPerson(PersonDTO view) {

        Person entity = mapperToEntity(view);

        peopleRepository.save(entity);

        return view;

    }

    public PersonDTO updatePersonByLastName(PersonDTO personRequest, Long id) {

        Person entity = findOneById(id);

        Person newPerson = mapperToEntity(personRequest);

        peopleRepository.save(newPerson);

        PersonDTO view = mapperToDTO(entity);

        return view;
    }

    public void deletePersonById(Long id) {
        peopleRepository.deleteById(id);
    }



/**
* Маппинг
*/
    private Person mapperToEntity(PersonDTO view){

        Person entity = new Person();

        entity.setFirstName(view.getFirstName());
        entity.setLastName(view.getLastName());
        entity.setGender(view.getGender());
        entity.setBirthday(view.getBirthday());
        entity.setEmail(view.getEmail());

        return entity;

    }

    private PersonDTO mapperToDTO (Person entity){

        PersonDTO view = new PersonDTO();

        view.setFirstName(entity.getFirstName());
        view.setLastName(entity.getLastName());
        view.setGender(entity.getGender());
        view.setBirthday(entity.getBirthday());
        view.setEmail(entity.getEmail());

        return view;
    }
}
