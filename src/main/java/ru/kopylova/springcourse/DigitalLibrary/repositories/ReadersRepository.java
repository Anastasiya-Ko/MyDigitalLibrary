package ru.kopylova.springcourse.DigitalLibrary.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Reader;

// Integer в JpaRepository<Person, Integer> говорит о типе первичного ключа
//Благодаря параметризации JpaRepository автоматически сгенерирует все нужные методы для сущности

public interface ReadersRepository extends JpaRepository<Reader, Long> {

    Page<Reader> findByLastNameLikeIgnoreCaseOrderByBirthdayDesc(String lastName, Pageable pageable);


}

