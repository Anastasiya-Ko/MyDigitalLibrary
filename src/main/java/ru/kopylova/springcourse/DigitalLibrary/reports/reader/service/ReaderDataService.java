package ru.kopylova.springcourse.DigitalLibrary.reports.reader.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.entity.Reader;
import ru.kopylova.springcourse.DigitalLibrary.readers.repository.ReadersRepository;
import ru.kopylova.springcourse.DigitalLibrary.reports.reader.models.view.ReaderDTOReport;

import java.util.*;

/**
 * Сервис данных для отчёта
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReaderDataService {

    ReadersRepository readersRepository;

    /**
     * Метод создания перечня читателей по возрастным группам
     */
    public Map<String, List<ReaderDTOReport>> readerGroupAge() {

        List<ReaderDTOReport> listView = createListDTO();

        Map<String, List<ReaderDTOReport>> result = new LinkedHashMap<>();

        result.put("Дети (до 10 лет включительно)", new ArrayList<>());
        result.put("Подростки (от 11 лет до 18 лет включительно)", new ArrayList<>());
        result.put("Взрослые (старше 18 лет)", new ArrayList<>());

        for (ReaderDTOReport reader : listView) {

            if (reader.getAge() <= 10) {
                result.get("Дети (до 10 лет включительно)").add(reader);
            } else if (reader.getAge() <= 18) {
                result.get("Подростки (от 11 лет до 18 лет включительно)").add(reader);
            } else {
                result.get("Взрослые (старше 18 лет)").add(reader);
            }
        }

        return result;
    }

    /**
     * Метод заполнения перечня дто для отчёта
     */
    private List<ReaderDTOReport> createListDTO() {
        List<Reader> listEntity = readersRepository.findAll();
        List<ReaderDTOReport> listView = new ArrayList<>();
        listEntity.sort(Comparator.comparing(Reader::getAge));

        for (Reader reader: listEntity) {
            listView.add(createDTO(reader));
        }
        return listView;
    }

    /**
     * Метод создания дто для отчёта
     */
    private ReaderDTOReport createDTO(Reader entity) {

        ReaderDTOReport view = new ReaderDTOReport();

        view.setId(""+ entity.getId());
        view.setFirstName(entity.getFirstName());
        view.setLastName(entity.getLastName());
        view.setAge(entity.getAge());

        return view;
    }

}
