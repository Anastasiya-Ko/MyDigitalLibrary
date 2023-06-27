package ru.kopylova.springcourse.DigitalLibrary.dictionary;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

/**
 * Набор констант для сортировки страницы с читателями
 */
@Getter
@RequiredArgsConstructor
public enum ReaderSort {

    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    LASTNAME_ASC(Sort.by(Sort.Direction.ASC, "lastName")),
    LASTNAME_DESC(Sort.by(Sort.Direction.DESC, "lastName")),
    BIRTHDAY_ASC(Sort.by(Sort.Direction.ASC, "birthday")),
    BIRTHDAY_DESC(Sort.by(Sort.Direction.DESC, "birthday"));

    private final Sort sortValue;
}
