package ru.kopylova.springcourse.DigitalLibrary.util.valid.capitalLetter;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Класс создания аннотации для проверки написания слова с заглавной буквы
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CapitalLetterValidator.class)
@Documented
public @interface CapitalLetter {
    String message() default "Поле должно быть с заглавной буквы";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
