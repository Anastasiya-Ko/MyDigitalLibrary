package ru.kopylova.springcourse.DigitalLibrary.util.valid.capitalLetter;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Класс, описывающий логику проверки написания слова с заглавной буквы
 */
public class CapitalLetterValidator implements ConstraintValidator<CapitalLetter, String> {

    /**
     * Метод для проверки валидности слова
     * @param value проверяемое слово
     * @param context контекстные данные для проверки ограничений
     * @return валидное/невалидное слово
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && !value.isEmpty()) {
            return Character.isUpperCase(value.charAt(0));
        }
        return true;
    }

}