package com.github.szilex94.edu.round_tracker.rest.jakarta.annotation.not_zero;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Repeatable(NotZero.List.class)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotZeroIntValidator.class})
public @interface NotZero {

    String message() default "Fields values don't match!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        NotZero[] value();
    }
}
