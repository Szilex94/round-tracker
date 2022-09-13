package com.github.szilex94.edu.round_tracker.service.user.profile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for {@link ProfileIdGenerator}
 *
 * @author szilex94
 */
public class ProfileIdGeneratorTest {

    private ProfileIdGenerator subject;

    @BeforeEach
    public void beforeEach() {
        this.subject = ProfileIdGenerator.defaultImpl();
    }

    @ParameterizedTest
    @MethodSource("test_defaultImpl_validArguments")
    public void test_defaultImpl(UserProfile up, String expectedResult) {
        var result = subject.apply(up);
        assertEquals(expectedResult, result);
    }

    private static Stream<Arguments> test_defaultImpl_validArguments() {
        return Stream.<Arguments>builder()
                .add(Arguments.of(genericUserProfile(), "firstName.lastName.alias"))
                .add(Arguments.of(genericUserProfile().setAlias(null), "firstName.lastName"))
                .add(Arguments.of(genericUserProfile().setAlias(""), "firstName.lastName"))
                .build();
    }

    @ParameterizedTest
    @MethodSource("test_defaultImpl_invalidArguments")
    public void test_defaultImpl_exceptionalCases(UserProfile up) {
        assertThrows(IllegalArgumentException.class, () -> subject.apply(up));
    }

    private static Stream<UserProfile> test_defaultImpl_invalidArguments() {
        return Stream.<UserProfile>builder()
                .add(genericUserProfile().setFirstName(null))
                .add(genericUserProfile().setFirstName(""))
                .add(genericUserProfile().setLastName(null))
                .add(genericUserProfile().setLastName(""))
                .build();
    }

    private static UserProfile genericUserProfile() {
        return new UserProfile()
                .setLastName("lastName")
                .setFirstName("firstName")
                .setAlias("alias");
    }

}
