package ru.kopylova.springcourse.DigitalLibrary.util.valid.genger;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenderNamePatternValidator implements ConstraintValidator<GenderNamePattern, CharSequence> {
    private List<String> acceptedValues;

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
