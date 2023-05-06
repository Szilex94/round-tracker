package com.github.szilex94.edu.round_tracker.rest.error;

import com.github.szilex94.edu.round_tracker.rest.error.codes.ApiErrorDetail;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import static com.github.szilex94.edu.round_tracker.rest.error.codes.SystemAPIError.SYSTEM_API_NOT_SUPPORTED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for {@link ErrorHandlingUtils}
 *
 * @author szilex94
 */
public class ErrorHandlingUtilsTest {

    @Test
    public void test_createNew_nullStatusCode() {
        assertThrows(IllegalArgumentException.class,
                () -> ErrorHandlingUtils.createNew(null, SYSTEM_API_NOT_SUPPORTED));
    }

    @Test
    public void test_createNew_nullApiErrorDetail() {
        assertThrows(IllegalArgumentException.class,
                () -> ErrorHandlingUtils.createNew(HttpStatus.BAD_REQUEST, null));
    }

    @Test
    public void test_createNew_happyFlow() {
        var result = ErrorHandlingUtils.createNew(HttpStatus.BAD_REQUEST, SYSTEM_API_NOT_SUPPORTED);
        assertEquals(SYSTEM_API_NOT_SUPPORTED.getTitle(), result.getTitle());
        assertEquals(SYSTEM_API_NOT_SUPPORTED.getApiErrorCode(), result.getProperties().get(ApiErrorDetail.API_ERROR_CODE));
    }

    @Test
    public void test_transferDetails_nullApiErrorDetail() {
        assertThrows(IllegalArgumentException.class,
                () -> ErrorHandlingUtils.transferDetails(null, ProblemDetail.forStatus(400)));
    }

    @Test
    public void test_transferDetails_nullProblemDetail() {
        assertThrows(IllegalArgumentException.class,
                () -> ErrorHandlingUtils.transferDetails(SYSTEM_API_NOT_SUPPORTED, null));
    }


    @Test
    public void test_transferDetails_happyFlow() {
        var initialDetail = ProblemDetail.forStatus(400);
        var result = ErrorHandlingUtils.transferDetails(SYSTEM_API_NOT_SUPPORTED, initialDetail);
        assertEquals(SYSTEM_API_NOT_SUPPORTED.getTitle(), result.getTitle());
        assertEquals(SYSTEM_API_NOT_SUPPORTED.getApiErrorCode(), result.getProperties().get(ApiErrorDetail.API_ERROR_CODE));
    }


}
