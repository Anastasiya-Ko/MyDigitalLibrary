package ru.kopylova.springcourse.DigitalLibrary.mappers;

import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.models.view.BookDTOEasy;
import ru.kopylova.springcourse.DigitalLibrary.models.view.BookDTORich;
import ru.kopylova.springcourse.DigitalLibrary.services.AuthorService;
import ru.kopylova.springcourse.DigitalLibrary.services.ReadersService;

@Service
public class BookMapper {

    @Autowired
    @NonFinal
    @Lazy
    private AuthorMapper authorMapper;

    @Autowired
    @NonFinal
    @Lazy
    private AuthorService authorService;

    @Autowired
    @NonFinal
    @Lazy
    private ReadersService readersService;

    @Autowired
    @NonFinal
    @Lazy
    private ReaderMapper readerMapper;


    public Book mapperToEntity(BookDTORich view, boolean isWrite) {
        Book entity = new Book();

        if(isWrite) {
            entity.setId(view.getId());
        }
        if (view.getAuthorOwner() != null) {
            authorService.readOneById(view.getAuthorOwner().getId());
            entity.setAuthorOwner(authorMapper.mapperToEntity(view.getAuthorOwner(), true));
        }

        if (view.getReaderDTOEasy() != null) {
            readersService.readOneById(view.getReaderDTOEasy().getId());
            entity.setReaderOwner(readerMapper.mapperToEntityEasy(view.getReaderDTOEasy(), true));

        }
        //TODO можно ли это упростить?
        if (view.getReaderDTOEasy() == null) {
            entity.setBookIsFree(true);
        } else entity.setBookIsFree(false);

        entity.setTitle(view.getTitle());
        entity.setYearOfPublication(view.getYearOfPublication());


        return entity;

    }

    public BookDTORich mapperToDTORich(Book entity, boolean isWrite) {

        BookDTORich view = new BookDTORich();
        if(isWrite) {
            view.setId(entity.getId());
        }
        if(entity.getAuthorOwner() != null){
            authorService.readOneById(entity.getAuthorOwner().getId());
            view.setAuthorOwner(authorMapper.mapperToDTO(entity.getAuthorOwner(), true));
        }

        if (entity.getReaderOwner() != null) {
            readersService.readOneById(entity.getReaderOwner().getId());
            view.setReaderDTOEasy(readerMapper.mapperToDTOEasy(entity.getReaderOwner(), true));

        }

        //TODO можно ли это упростить?
        if (entity.getReaderOwner() == null) {
            view.setBookIsFree(true);
        }else{
            view.setBookIsFree(false);
        }

        view.setTitle(entity.getTitle());
        view.setYearOfPublication(entity.getYearOfPublication());

        return view;
    }

    //TODO как избавиться от этого, если он нужен?
    public BookDTOEasy mapperToDTOEasy(Book entity, boolean isWrite) {

        BookDTOEasy view = new BookDTOEasy();
        if(isWrite) {
            view.setId(entity.getId());
        }

        if(entity.getAuthorOwner() != null){
            authorService.readOneById(entity.getAuthorOwner().getId());
            view.setAuthorOwner(authorMapper.mapperToDTO(entity.getAuthorOwner(), true));
        }

        view.setTitle(entity.getTitle());
        view.setYearOfPublication(entity.getYearOfPublication());

        return view;
    }
}
