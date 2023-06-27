package ru.kopylova.springcourse.DigitalLibrary.readers.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.kopylova.springcourse.DigitalLibrary.readers.mapper.ReaderMapper;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.entity.Reader;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.view.ReaderDTORich;
import ru.kopylova.springcourse.DigitalLibrary.readers.repository.ReadersRepository;


/**
 * Сервис для работы с читателями
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReadersService {

    ReadersRepository readersRepository;
    ReaderMapper readerMapper;

    /**
     * Внесение нового читателя в бд
     * @param view представление, с полной информацией о читателе, для занесения в бд
     */
    public ReaderDTORich createReader(ReaderDTORich view) {

        Reader entity = readerMapper.mapperToEntityRich(view, false);
        readersRepository.save(entity);

        return readerMapper.mapperToDTORich(entity, true);

    }

    /**
     * Обновление уже существующего читателя
     * @param view представление, с полной информацией о читателе, для занесения в бд
     */
    public ReaderDTORich updateReader(ReaderDTORich view) {

        //поиск читателя по id, взятого из входящего представления
        var updateEntity = getById(view.getId());

        updateEntity = readerMapper.mapperToEntityRich(view, true);
        readersRepository.save(updateEntity);

        return readerMapper.mapperToDTORich(updateEntity, true);

    }

    /**
     * Удаление читателя по его идентификатору
     * @return Информационное сообщение
     */
    public String deleteReaderById(Long id) {

        //проверка наличия искомого читателя в бд
        Reader entity = getById(id);

        readersRepository.deleteById(id);
        return String.format("Читатель с фамилией \"%s\" успешно удалён", entity.getLastName());
    }

    /**
     * Постраничный вывод всех читателей из бд
     */
    public Page<ReaderDTORich> readAllReader(Pageable pageable) {
        Page<Reader> entityPage = readersRepository.findAll(pageable);
        return entityPage.map(entity -> readerMapper.mapperToDTORich(entity, true));
    }

    /**
     * Чтение информации о читателе по его id
     */
    public ReaderDTORich readOneById(Long readerId) {
        return readerMapper.mapperToDTORich(getById(readerId), true);
    }

    /**
     * Постраничный вывод читателей по фамилии
     */
    public Page<ReaderDTORich> readByLastName(String lastName, Pageable pageable) {

        String ex = String.format(("Читатель с фамилией \"%s\" не найден"), lastName);

        Page<Reader> entityPage = readersRepository.findByLastNameLikeIgnoreCaseOrderByBirthdayDesc(lastName, pageable);

        //если читатели с такой фамилией не найдены в бд, то выбросить исключение
        if (entityPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex);
        }

        return entityPage.map(entity -> readerMapper.mapperToDTORich(entity, true));
    }

    /**
     * Метод для получения человека по его идентификатору
     */
    public Reader getById(Long id) {
        String ex = String.format(("Читатель с номером = %d не найден"), id);
        return readersRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, ex));
    }

}
