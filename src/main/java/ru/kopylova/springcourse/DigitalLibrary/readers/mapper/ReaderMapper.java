package ru.kopylova.springcourse.DigitalLibrary.readers.mapper;

import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.dictionary.Gender;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.entity.Reader;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.view.ReaderDTOEasy;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.view.ReaderDTORich;

/**
 * Сопоставление данных читателя
 */
@Service
public class ReaderMapper {

    /**
     * Маппинг представления в энтити.
     * Используется для создании нового читателя и обновлении существующей
     * @param view входящее представление, с полной информацией о читателе
     * @param isWrite отвечает за запись id
     * @return сущность читателя
     */
    public Reader mapperToEntityRich(ReaderDTORich view, boolean isWrite) {
        Reader entity = new Reader();

        if (isWrite) {
            entity.setId(view.getId());
        }
        entity.setFirstName(view.getFirstName());
        entity.setLastName(view.getLastName());
        entity.setGender(Gender.valueOf(view.getGender()));
        entity.setBirthday(view.getBirthday());
        entity.setEmail(view.getEmail());
        entity.setAge(view.getAge());

        return entity;

    }

    /**
     * Маппинг сущности в дто, с полной информацией о читателе
     * @param entity сущность читателя
     * @param isWrite отвечает за запись id читателя
     * @return дто, с полной информацией о читателе
     */
    public ReaderDTORich mapperToDTORich(Reader entity, boolean isWrite) {

        ReaderDTORich view = new ReaderDTORich();

        if (isWrite) {
            view.setId(entity.getId());
        }
        view.setFirstName(entity.getFirstName());
        view.setLastName(entity.getLastName());
        view.setGender(String.valueOf(entity.getGender()));
        view.setBirthday(entity.getBirthday());
        view.setEmail(entity.getEmail());
        view.setAge(entity.getAge());

        return view;
    }

    /**
     * Маппинг сущности в представление, с сокращённой информацией о читателе.
     * Используется при создании "расширенной" дто читателя
     * @param entity сущность читателя
     * @param isWrite возможность записи id
     * @return "сокращённое" представление читателя
     */
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
