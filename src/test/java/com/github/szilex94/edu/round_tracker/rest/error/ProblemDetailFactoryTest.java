package com.github.szilex94.edu.round_tracker.rest.error;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static com.github.szilex94.edu.round_tracker.rest.error.codes.SystemAPIError.SYSTEM_API_NOT_SUPPORTED;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ProblemDetailFactory}
 *
 * @author szilex94
 */
public class ProblemDetailFactoryTest {

    private static final String TEST_WIKI_URI = "http://localhost:8080/test";

    public ProblemDetailFactory subject;

    @BeforeEach
    public void beforeEach() {
        this.subject = new ProblemDetailFactory(TEST_WIKI_URI);
    }

    @Test
    public void test_createAndInitiate_invalidHTTPStatusCode() {
        assertThrows(IllegalArgumentException.class,
                () -> subject.createAndInitiate(null, SYSTEM_API_NOT_SUPPORTED));
    }

    @Test
    public void test_createAndInitiate_invalidApiErrorDetail() {
        assertThrows(IllegalArgumentException.class,
                () -> subject.createAndInitiate(HttpStatus.BAD_REQUEST, null));
    }

    @Test
    public void test_createAndInitiate_happyFlow() {
        var result = subject.createAndInitiate(HttpStatus.BAD_REQUEST, SYSTEM_API_NOT_SUPPORTED);

        var expectedType = URI.create(TEST_WIKI_URI + '#' + SYSTEM_API_NOT_SUPPORTED.getApiErrorCode());
        assertEquals(expectedType, result.getType());

        assertEquals(SYSTEM_API_NOT_SUPPORTED.getTitle(), result.getTitle());

        var additionalProperties = result.getProperties();

        assertNotNull(additionalProperties);
        assertEquals(SYSTEM_API_NOT_SUPPORTED.getApiErrorCode(), additionalProperties.get(ProblemDetailFactory.API_ERROR_CODE));

        String timeStamp = (String) additionalProperties.get(ProblemDetailFactory.TIME_STAMP);

        assertNotNull(timeStamp);
        OffsetDateTime.parse(timeStamp, DateTimeFormatter.ISO_DATE_TIME);
    }
}
