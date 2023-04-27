package com.github.szilex94.edu.round_tracker.rest.jakarta.annotation.not_zero;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link NotZeroIntValidator}
 *
 * @author szilex94
 */
public class NotZeroIntValidatorTest {

    private Validator validator;

    @BeforeEach
    public void beforeEach() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void test_constraintWithZero() {
        var entity = new TestEntity(0);
        var result = validator.validate(entity);
        assertFalse(result.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -1, 1, Integer.MAX_VALUE})
    public void test_constraintWithValidNumbers(int input) {
        var entity = new TestEntity(input);
        var result = validator.validate(entity);
        assertTrue(result.isEmpty());
    }

    private record TestEntity(
            @NotZero int number
    ) {

    }


}
