package ru.kopylova.springcourse.DigitalLibrary.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Person;
import ru.kopylova.springcourse.DigitalLibrary.models.view.PersonDTO;
import ru.kopylova.springcourse.DigitalLibrary.repositories.PeopleRepository;



@Service
public class PeopleService {

    final PeopleRepository peopleRepository;


    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    public PersonDTO createPerson(PersonDTO view) {

        Person entity = mapperToEntity(view);
        peopleRepository.save(entity);

        return mapperToDTO(entity);

    }


    public PersonDTO updatePerson(PersonDTO personRequest, Long id) {

        Person person = getById(id);

        Person newPerson = mapperToEntity(personRequest);
        newPerson.setId(person.getId());

        peopleRepository.save(newPerson);

        return mapperToDTO(newPerson);

    }

    public String deletePersonById(Long id) {

        getById(id);

        peopleRepository.deleteById(id);

        return String.format("Пользователь с ID = %d удалён", id);
    }

    public Page<PersonDTO> readAllPeople(Pageable pageable) {

        Page<Person> entityPage = peopleRepository.findAll(pageable);
        return entityPage.map(this::mapperToDTO);

    }

    public PersonDTO readOneById(Long id) {
        return mapperToDTO(getById(id));
    }


    public Page<PersonDTO> readByLastName(String lastName, Pageable pageable) {

        String ex = String.format(("Пользователь с фамилией = %s не найден"), lastName);

        Page<Person> entityList = peopleRepository.findByLastNameLikeIgnoreCaseOrderByBirthdayDesc(lastName, pageable);

        if (entityList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityList.map(this::mapperToDTO);

    }

    /**
     * Получить по идентификатору
     */
    private Person getById(Long id) {
        String ex = String.format(("Пользователь с ID = %d не найден"), id);
        return peopleRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, ex));
    }


    /**
     * Маппинг
     */

    private Person mapperToEntity(PersonDTO view) {
        Person entity = new Person();

        entity.setFirstName(view.getFirstName());
        entity.setLastName(view.getLastName());
        entity.setGender(view.getGender());
        entity.setBirthday(view.getBirthday());
        entity.setEmail(view.getEmail());
        entity.setAge(view.getAge());

        return entity;

    }

    private PersonDTO mapperToDTO(Person entity) {

        PersonDTO view = new PersonDTO();

        //view.setId(entity.getId());
        view.setFirstName(entity.getFirstName());
        view.setLastName(entity.getLastName());
        view.setGender(entity.getGender());
        view.setBirthday(entity.getBirthday());
        view.setEmail(entity.getEmail());
        view.setAge(entity.getAge());

        return view;
    }


}
