package ru.kopylova.springcourse.DigitalLibrary.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
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


    public List<Person> findAll() {
        return peopleRepository.findAll();
    }
    public Person findOneById(Long id) {
        return peopleRepository.findById(id).orElse(null);
    }
    public Person findByLastName(String lastName) {
        return peopleRepository.findByName(lastName);
    }

    public PersonDTO createPerson(PersonDTO view) {

        Person entity = new Person();

        // мапинг
        entity.setFirstName(view.getFirstName());
        entity.setLastName(view.getLastName());
        entity.setGender(view.getGender());
        entity.setBirthday(view.getBirthday());
        entity.setEmail(view.getEmail());

        peopleRepository.save(entity);

        return view;
    }

    public PersonDTO updatePersonByLastName(PersonDTO personRequest, String name) {
        Person entity = peopleRepository.findByName(name);

        entity.setFirstName(personRequest.getFirstName());
        entity.setLastName(personRequest.getLastName());
        entity.setGender(personRequest.getGender());
        entity.setBirthday(personRequest.getBirthday());
        entity.setEmail(personRequest.getEmail());

        return personRequest;
    }



    public void deletePersonById(Long id) {
        peopleRepository.deleteById(id);
    }


}
