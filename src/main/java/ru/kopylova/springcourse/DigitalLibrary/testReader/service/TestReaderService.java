package ru.kopylova.springcourse.DigitalLibrary.testReader.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.testReader.mapper.TestReaderMapper;
import ru.kopylova.springcourse.DigitalLibrary.testReader.model.view.TestReaderDTO;
import ru.kopylova.springcourse.DigitalLibrary.testReader.repository.TestReaderRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с тестовыми читателями
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestReaderService {

    TestReaderRepository testReaderRepository;
    TestReaderMapper testReaderMapper;


    public List<TestReaderDTO> readAllTestReaders() {
        return testReaderRepository.findAll().stream().map(testReaderMapper::mapperToDTO).collect(Collectors.toList());
    }
}
