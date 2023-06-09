package ru.kopylova.springcourse.DigitalLibrary.readers.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kopylova.springcourse.DigitalLibrary.readers.models.entity.Reader;

// Integer в JpaRepository<Person, Integer> говорит о типе первичного ключа
//Благодаря параметризации JpaRepository автоматически сгенерирует все нужные методы для сущности

/**
 * Репозиторий для связи с таблицей Читатель в бд
 */
public interface ReadersRepository extends JpaRepository<Reader, Long>{

    /**
     * Поиск читателей по фамилии, без учёта кейса и с убывающей сортировкой по дню рождения
     * @param lastName фамилия читателя
     */
    Page<Reader> findByLastNameLikeIgnoreCaseOrderByBirthdayDesc(String lastName, Pageable pageable);


}

