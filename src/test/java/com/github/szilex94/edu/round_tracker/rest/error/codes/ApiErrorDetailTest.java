package com.github.szilex94.edu.round_tracker.rest.error.codes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ApiErrorDetail}
 *
 * @author szilex94
 */
public class ApiErrorDetailTest {

    private static final Map<String, ApiErrorDetail> KNOWN_ERROR_DETAILS = Collections.unmodifiableMap(ImmutableList.<ApiErrorDetail>builder()
            .add(SystemAPIError.values())
            .add(UserAPIError.values())
            .add(SupportAPIError.values())
            .add(TrackingAPIError.values())
            .build()
            .stream()
            .collect(Collectors.toMap(ApiErrorDetail::getApiErrorCode, identity())));

    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {"randomText", "moreRandomStuff"})
    public void test_findByCode_unknownCodes(String code) {
        var result = ApiErrorDetail.findByCode(code);
        assertNull(result);
    }

    private static Stream<String> errorCodesSource() {
        return KNOWN_ERROR_DETAILS.keySet().stream();
    }

    @ParameterizedTest
    @MethodSource("errorCodesSource")
    public void test_findByCode_knownCodes(String code) {
        final var result = ApiErrorDetail.findByCode(code);
        final var expected = KNOWN_ERROR_DETAILS.get(code);

        assertNotNull(result);
        assertSame(expected, result);
    }

    /**
     * This test ensures that newly added implementations are also included in the unit test
     */
    @Test
    public void test_ensureNewMembersProperlyAdded() {
        assertEquals(KNOWN_ERROR_DETAILS.size(), Support.CODE_TO_ERRORS.size());
        for (var entry : Support.CODE_TO_ERRORS.entrySet()) {
            var knownByUnitTest = KNOWN_ERROR_DETAILS.get(entry.getKey());
            assertNotNull(knownByUnitTest, "The following code was not defined: " + entry.getKey());
        }
    }

}
