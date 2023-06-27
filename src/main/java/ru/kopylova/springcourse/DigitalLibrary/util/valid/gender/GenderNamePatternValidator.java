package ru.kopylova.springcourse.DigitalLibrary.util.valid.gender;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Класс, описывающий логику проверки соответствия поля половой принадлежности читателя - заявленному перечислению
 */
public class GenderNamePatternValidator implements ConstraintValidator<GenderNamePattern, CharSequence> {

    /**
     * Лист переданных значений
     */
    private List<String> acceptedValues;

    /**
     * Метод для проверки переданных значений - значениям, имеющимся в энаме
     * @param constraint
     */
    @Override
    public void initialize(GenderNamePattern constraint) {
        acceptedValues = Stream.of(constraint.enumClass().getEnumConstants()).map(Enum::name).collect(Collectors.toList());

    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        return acceptedValues.contains(value.toString());
    }

}
