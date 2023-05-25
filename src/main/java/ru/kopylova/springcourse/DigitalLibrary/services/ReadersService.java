package ru.kopylova.springcourse.DigitalLibrary.services;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.kopylova.springcourse.DigitalLibrary.mappers.ReaderMapper;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Reader;
import ru.kopylova.springcourse.DigitalLibrary.models.view.ReaderDTORich;
import ru.kopylova.springcourse.DigitalLibrary.repositories.ReadersRepository;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReadersService {

    ReadersRepository readersRepository;

    ReaderMapper readerMapper;

    public ReaderDTORich createReader(ReaderDTORich view) {

        Reader entity = readerMapper.mapperToEntityRich(view, false);
        readersRepository.save(entity);

        return readerMapper.mapperToDTORich(entity, true);

    }

    //TODO нужно подумать о надобности входящего id + как сделать валидацию гендера???или это невозможно. Т.е. Яна может быть только жен рода

    public ReaderDTORich updateReader(ReaderDTORich personRequest, Long id) {

        Reader entitySearch = getById(id);

        Reader newReader = readerMapper.mapperToEntityRich(personRequest, false);
        newReader.setId(entitySearch.getId());

        readersRepository.save(newReader);

        return readerMapper.mapperToDTORich(newReader, true);

    }

    public String deleteReaderById(Long id) {

        getById(id);

        readersRepository.deleteById(id);

        return String.format("Читатель с id = %d успешно удалён", id);
    }

    public Page<ReaderDTORich> readAllReader(Pageable pageable) {
        Page<Reader> entityPage = readersRepository.findAll(pageable);

        return entityPage.map(entity -> readerMapper.mapperToDTORich(entity, true));
    }

    public ReaderDTORich readOneById(Long id) {
        return readerMapper.mapperToDTORich(getById(id), true);
    }


    public Page<ReaderDTORich> readByLastName(String lastName, Pageable pageable) {

        String ex = String.format(("Читатель с фамилией = %s не найден"), lastName);

        Page<Reader> entityPage = readersRepository.findByLastNameLikeIgnoreCaseOrderByBirthdayDesc(lastName, pageable);

        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityPage.map(entity -> readerMapper.mapperToDTORich(entity, true));
    }

    /**
     * Метод внутреннего пользования для получения человека по его идентификатору
     */
    private Reader getById(Long id) {
        String ex = String.format(("Читатель с id = %d не найден"), id);
        return readersRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, ex));
    }

}
