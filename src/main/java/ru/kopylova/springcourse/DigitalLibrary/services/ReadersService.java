package ru.kopylova.springcourse.DigitalLibrary.services;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Reader;
import ru.kopylova.springcourse.DigitalLibrary.models.view.ReaderDTOEasy;
import ru.kopylova.springcourse.DigitalLibrary.models.view.ReaderDTORich;
import ru.kopylova.springcourse.DigitalLibrary.repositories.ReadersRepository;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReadersService {

    ReadersRepository readersRepository;

    public ReaderDTORich createReader(ReaderDTORich view) {

        Reader entity = mapperToEntityRich(view, false);
        readersRepository.save(entity);

        return mapperToDTORich(entity, true);

    }

    //TODO нужно подумать о надобности входящего id
    public ReaderDTORich updateReader(ReaderDTORich personRequest, Long id) {

        Reader entitySearch = getById(id);

        Reader newReader = mapperToEntityRich(personRequest, false);
        newReader.setId(entitySearch.getId());

        readersRepository.save(newReader);

        return mapperToDTORich(newReader, true);

    }

    public String deleteReaderById(Long id) {

        getById(id);

        readersRepository.deleteById(id);

        return String.format("Пользователь с ID = %d успешно удалён", id);
    }

    public Page<ReaderDTORich> readAllReader(Pageable pageable) {
        Page<Reader> entityPage = readersRepository.findAll(pageable);

        return entityPage.map(entity -> mapperToDTORich(entity, true));
    }

    public ReaderDTORich readOneById(Long id) {
        return mapperToDTORich(getById(id), true);
    }


    public Page<ReaderDTORich> readByLastName(String lastName, Pageable pageable) {

        String ex = String.format(("Пользователь с фамилией = %s не найден"), lastName);

        Page<Reader> entityPage = readersRepository.findByLastNameLikeIgnoreCaseOrderByBirthdayDesc(lastName, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityPage.map(entity -> mapperToDTORich(entity, true));
    }

    /**
     * Метод внутреннего пользования для получения человека по его идентификатору
     */
    private Reader getById(Long id) {
        String ex = String.format(("Пользователь с ID = %d не найден"), id);
        return readersRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, ex));
    }

    public Reader mapperToEntityRich(ReaderDTORich view, boolean isWrite) {
        Reader entity = new Reader();

        if (isWrite) {
            entity.setId(view.getId());
        }
        entity.setFirstName(view.getFirstName());
        entity.setLastName(view.getLastName());
        entity.setGender(view.getGender());
        entity.setBirthday(view.getBirthday());
        entity.setEmail(view.getEmail());
        entity.setAge(view.getAge());

        return entity;

    }

    public ReaderDTORich mapperToDTORich(Reader entity, boolean isWrite) {

        ReaderDTORich view = new ReaderDTORich();

        if (isWrite) {
            view.setId(entity.getId());
        }
        view.setFirstName(entity.getFirstName());
        view.setLastName(entity.getLastName());
        view.setGender(entity.getGender());
        view.setBirthday(entity.getBirthday());
        view.setEmail(entity.getEmail());
        view.setAge(entity.getAge());

        return view;
    }

    public Reader mapperToEntityEasy(ReaderDTOEasy view, boolean isWrite) {
        Reader entity = new Reader();

        if (isWrite) {
            entity.setId(view.getId());
        }
        entity.setFirstName(view.getFirstName());
        entity.setLastName(view.getLastName());

        return entity;

    }

    public ReaderDTOEasy mapperToDTOEasy(Reader entity, boolean isWrite) {

        ReaderDTOEasy view = new ReaderDTOEasy();

        if (isWrite) {
            view.setId(entity.getId());
        }
        view.setFirstName(entity.getFirstName());
        view.setLastName(entity.getLastName());

        return view;
    }


}
