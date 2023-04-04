package ru.kopylova.springcourse.DigitalLibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kopylova.springcourse.DigitalLibrary.models.entity.Person;

// Integer в JpaRepository<Person, Integer> говорит о типе первичного ключа
//Благодаря параметризации JpaRepository автоматически сгенерирует все нужные методы для сущности

public interface PeopleRepository extends JpaRepository<Person, Long> {
}
