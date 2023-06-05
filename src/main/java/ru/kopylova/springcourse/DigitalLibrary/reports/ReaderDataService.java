package ru.kopylova.springcourse.DigitalLibrary.reports;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Reader;
import ru.kopylova.springcourse.DigitalLibrary.models.view.ReaderDTOReport;
import ru.kopylova.springcourse.DigitalLibrary.repositories.ReadersRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReaderDataService {
    ReadersRepository readersRepository;

    public ReaderDTOReport createDTO(Reader entity) {

        ReaderDTOReport view = new ReaderDTOReport();

        view.setId(""+ entity.getId());
        view.setFirstName(entity.getFirstName());
        view.setLastName(entity.getLastName());
        view.setAge(entity.getAge());

        return view;
    }

    public List<ReaderDTOReport> createListDTO() {
        List<Reader> listEntity = readersRepository.findAll();
        List<ReaderDTOReport> listView = new ArrayList<>();

        for (Reader reader: listEntity) {
            listView.add(createDTO(reader));
        }
        return listView;
    }

    public void readerGroupAge() {

        var entityPage = createListDTO();

        Map<String, List<ReaderDTOReport>> result = new LinkedHashMap<>();
        result.put("Дети", new ArrayList<>());
        result.put("Подростки", new ArrayList<>());
        result.put("Взрослые", new ArrayList<>());

        for (ReaderDTOReport reader : entityPage) {

            if (reader.getAge() <= 10) {
                result.get("Дети").add(reader);
            } else if (reader.getAge() <= 18) {
                result.get("Подростки").add(reader);
            } else {
                result.get("Взрослые").add(reader);
            }
        }
    }
}
