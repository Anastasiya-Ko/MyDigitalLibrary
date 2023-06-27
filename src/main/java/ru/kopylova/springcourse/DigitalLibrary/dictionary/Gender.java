package ru.kopylova.springcourse.DigitalLibrary.dictionary;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Набор констант для определения половой принадлежности читателя
 */
@Getter
@RequiredArgsConstructor
public enum Gender {
    Муж,
    Жен
}
