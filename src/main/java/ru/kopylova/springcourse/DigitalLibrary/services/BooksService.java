package ru.kopylova.springcourse.DigitalLibrary.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Book;
import ru.kopylova.springcourse.DigitalLibrary.repositories.BooksRepository;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BooksService {

    final BooksRepository booksRepository;

    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(Pageable pageable, Sort sort) {
        return booksRepository.findAll(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("yearOfPublication"))).getContent();
    }
//    public BookDTO create (BookDTO view) {
//
//        // Создаем представление таблицы
//        Book entity = new Book();
//
//        // Перекладываем из view в entity
//        entity.setName(view.getName());
//
//
//        // Сохраняем
//        booksRepository.save(entity);
//
//        // ВОзвращаем клиенту
//        return view;
//    }
}
