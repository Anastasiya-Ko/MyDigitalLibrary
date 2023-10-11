package ru.kopylova.springcourse.DigitalLibrary.testReader.mapper;

import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.testReader.models.entity.TestReader;
import ru.kopylova.springcourse.DigitalLibrary.testReader.models.view.TestReaderDTO;


/**
 * Сопоставление данных тестового читателя
 */
@Service
public class TestReaderMapper {

    /**
     * Маппинг представления в энтити.
     * Используется прр создании новой книги и обновлении существующей
     * @param view входящее представление, с полной информацией о читателе
     * @return сущность читателя
     */
    public TestReader mapperToEntity(TestReaderDTO view) {

        TestReader entity = new TestReader();

        entity.setId(view.getId());
        entity.setName(view.getName());
        entity.setLastName(view.getLastName());

        return entity;

    }

    /**
     * Маппинг сущности в дто, с полной информацией о читателе
     * @param entity сущность читателя
     * @return дто, с полной информацией о читателе
     */
    public TestReaderDTO mapperToDTO(TestReader entity) {

        TestReaderDTO view = new TestReaderDTO();

        view.setId(entity.getId());
        view.setName(entity.getName());
        view.setLastName(entity.getLastName());

        return view;
    }
}
