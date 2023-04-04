package ru.kopylova.springcourse.DigitalLibrary.services;

import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Person;
import ru.kopylova.springcourse.DigitalLibrary.repositories.PeopleRepository;

import java.util.List;

@Service

public class PeopleService {

    private final PeopleRepository peopleRepository;

    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }


    public Person findOne(Long id) {
        return peopleRepository.findById(id).orElse(null);
    }


    public void savePerson(Person person) {
        peopleRepository.save(person);
    }


    public void updatePerson(Long id, Person updatePerson) {
        updatePerson.setId(id);
        peopleRepository.save(updatePerson);
    }

    public void deletePerson(Long id) {
        peopleRepository.deleteById(id);
    }

    /**
     * Создание человека
     * @param view
     * @return
     */
//    public PersonDTO create(PersonDTO view) {
//
//        Person entity = new Person();
//
//        // мапинг
//        entity.setName(view.getName());
//
//        peopleRepository.save(entity);
//
//
//        return view;
//
//    }
}
