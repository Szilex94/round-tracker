package com.github.szilex94.edu.round_tracker.rest.jakarta.annotation.not_zero;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotZeroIntValidator implements ConstraintValidator<NotZero, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != 0;
    }
}
