package ru.kopylova.springcourse.DigitalLibrary.testReader.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kopylova.springcourse.DigitalLibrary.testReader.entity.TestReader;

public interface TestReaderRepository extends JpaRepository<TestReader, Long> {
}
