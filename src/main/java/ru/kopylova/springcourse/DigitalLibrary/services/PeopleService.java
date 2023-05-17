package ru.kopylova.springcourse.DigitalLibrary.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Person;
import ru.kopylova.springcourse.DigitalLibrary.models.view.PersonDTORich;
import ru.kopylova.springcourse.DigitalLibrary.repositories.PeopleRepository;


@Service
public class PeopleService {

    private final PeopleRepository peopleRepository;

    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    public PersonDTORich createPerson(PersonDTORich view) {

        Person entity = mapperToEntity(view, false);
        peopleRepository.save(entity);

        return mapperToDTO(entity, true);

    }


    public PersonDTORich updatePerson(PersonDTORich personRequest, Long id) {

        Person person = getById(id);

        Person newPerson = mapperToEntity(personRequest, false);

        peopleRepository.save(newPerson);

        return mapperToDTO(newPerson, false);

    }

    public String deletePersonById(Long id) {

        getById(id);

        peopleRepository.deleteById(id);

        return String.format("Пользователь с ID = %d успешно удалён", id);
    }

    public Page<PersonDTORich> readAllPeople(Pageable pageable) {
        Page<Person> entityPage = peopleRepository.findAll(pageable);

        return entityPage.map(entity -> mapperToDTO(entity, true));
    }

    public PersonDTORich readOneById(Long id) {
        return mapperToDTO(getById(id), true);
    }

    public Page<PersonDTORich> readByLastName(String lastName, Pageable pageable) {

        String ex = String.format(("Пользователь с фамилией = %s не найден"), lastName);

        Page<Person> entityPage = peopleRepository.findByLastNameLikeIgnoreCaseOrderByBirthdayDesc(lastName, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityPage.map(entity -> mapperToDTO(entity, true));
    }

    /**
     * Метод внутреннего пользования для получения человека по его идентификатору
     */
    private Person getById(Long id) {
        String ex = String.format(("Пользователь с ID = %d не найден"), id);
        return peopleRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, ex));
    }

    public Person mapperToEntity(PersonDTORich view, boolean isWrite) {
        Person entity = new Person();

        if (isWrite) {
            entity.setId(view.getId());
        }
        entity.setFirstName(view.getFirstName());
        entity.setLastName(view.getLastName());
        entity.setGender(view.getGender());
        entity.setBirthday(view.getBirthday());
        entity.setEmail(view.getEmail());
        entity.setAge(view.getAge());
       // entity.setBooks(view.getBooks());

        return entity;

    }

    public PersonDTORich mapperToDTO(Person entity, boolean isWrite) {

        PersonDTORich view = new PersonDTORich();

        if (isWrite) {
            view.setId(entity.getId());
        }
        view.setFirstName(entity.getFirstName());
        view.setLastName(entity.getLastName());
        view.setGender(entity.getGender());
        view.setBirthday(entity.getBirthday());
        view.setEmail(entity.getEmail());
        view.setAge(entity.getAge());
      //  view.setBooks(entity.getBooks());

        return view;
    }


}
