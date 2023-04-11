package ru.kopylova.springcourse.DigitalLibrary.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Person;
import ru.kopylova.springcourse.DigitalLibrary.models.view.PersonDTO;
import ru.kopylova.springcourse.DigitalLibrary.repositories.PeopleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PeopleService {

    final PeopleRepository peopleRepository;


    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    // да
    public PersonDTO createPerson(PersonDTO view) {

        Person entity = mapperToEntity(view);
        peopleRepository.save(entity);

        return mapperToDTO(entity);

    }

    // можешь убрать маппер за ненадобностью
    public PersonDTO updatePersonById(PersonDTO personRequest) {

        Person entity = getOne(personRequest.getId());

        entity.setId(personRequest.getId());
        entity.setFirstName(personRequest.getFirstName());
        entity.setLastName(personRequest.getLastName());
        entity.setGender(personRequest.getGender());
        entity.setBirthday(personRequest.getBirthday());
        entity.setEmail(personRequest.getEmail());


        peopleRepository.save(entity);

        return mapperToDTO(entity);

    }

    // да
    public String deletePersonById(Long id) {

        findOneById(id);

        peopleRepository.deleteById(id);

        return String.format("Пользователь с ID = %d удалён", id);
    }

    /**
     * Поиск
     */

    private Person getOne(Long id) {
        String ex = String.format(("Пользователь с ID = %d не найден"), id);

        return peopleRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, ex));
    }
    public PersonDTO findOneById(Long id) {

        return mapperToDTO(getOne(id));
    }
    //TODO: настроить пагинацию
    public List<PersonDTO> readAllPeople(Pageable pageable) {

        // круто сделала! мне такое стало достпно гораздо позже-естественно, что я скопипастила
        return peopleRepository.findAll().stream()
                .map(this::mapperToDTO)
                .collect(Collectors.toList());
    }

    public List<PersonDTO> findByLastName(String lastName) {

        String ex = String.format(("Пользователь с фамилией = %s не найден"), lastName);

        List<Person> personList = peopleRepository.findByLastNameOrderByBirthday(lastName);

        if (personList.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return personList.stream().map(this::mapperToDTO).collect(Collectors.toList());

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


        return entity;

    }

    private PersonDTO mapperToDTO(Person entity) {

        PersonDTO view = new PersonDTO();

        view.setId(entity.getId());
        view.setFirstName(entity.getFirstName());
        view.setLastName(entity.getLastName());
        view.setGender(entity.getGender());
        view.setBirthday(entity.getBirthday());
        view.setEmail(entity.getEmail());

        return view;
    }


}
