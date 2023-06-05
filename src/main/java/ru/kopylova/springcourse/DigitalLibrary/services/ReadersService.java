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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

    public ReaderDTORich updateReader(ReaderDTORich view) {

        var updateEntity = getById(view.getId());
        updateEntity = readerMapper.mapperToEntityRich(view, true);
        readersRepository.save(updateEntity);
        return readerMapper.mapperToDTORich(updateEntity, true);

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

    public void readAllReaderddd() {

        var entityPage = readersRepository.findAll();

        Map<String, List<Reader>> result = new LinkedHashMap<>();
        result.put("Дети", new ArrayList<>());
        result.put("Подростки", new ArrayList<>());
        result.put("Взрослые", new ArrayList<>());

        for (Reader r : entityPage) {

            if (r.getAge() <= 10) {
                result.get("Дети").add(r);
            } else if (r.getAge() <= 18) {
                result.get("Подростки").add(r);
            } else {
                result.get("Взрослые").add(r);
            }

        }

        var test = entityPage.stream().filter(r -> (r.getAge() < 10 && r.getAge() < 5)).collect(Collectors.groupingBy(Reader::getAge));

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
