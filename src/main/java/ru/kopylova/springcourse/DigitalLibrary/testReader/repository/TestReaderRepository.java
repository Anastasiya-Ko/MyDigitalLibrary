package ru.kopylova.springcourse.DigitalLibrary.testReader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kopylova.springcourse.DigitalLibrary.testReader.model.entity.TestReader;

public interface TestReaderRepository extends JpaRepository<TestReader, Long> {
}
