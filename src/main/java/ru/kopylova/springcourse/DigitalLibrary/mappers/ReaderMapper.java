package ru.kopylova.springcourse.DigitalLibrary.mappers;

import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Reader;
import ru.kopylova.springcourse.DigitalLibrary.models.view.ReaderDTOEasy;
import ru.kopylova.springcourse.DigitalLibrary.models.view.ReaderDTORich;

@Service
public class ReaderMapper {
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
