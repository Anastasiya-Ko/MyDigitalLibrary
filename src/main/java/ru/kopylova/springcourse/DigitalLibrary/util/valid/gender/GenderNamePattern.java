package ru.kopylova.springcourse.DigitalLibrary.util.valid.gender;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Создание аннотации для проверки соответствия поля половой принадлежности читателя - заявленному перечислению
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = GenderNamePatternValidator.class)
@Documented
public @interface GenderNamePattern {
    Class<? extends Enum<?>> enumClass();

    String message() default "Поле должно соответствовать: Муж - для мужчины, Жен - для женщины";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
