package com.github.szilex94.edu.round_tracker.rest.error.codes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Intended to be implemented by enumerations which detail anticipated problems.
 *
 * @author szilex94
 */
public sealed interface ApiErrorDetail permits SystemAPIError,
        UserAPIError,
        SupportAPIError,
        TrackingAPIError {

    static ApiErrorDetail findByCode(String code) {
        return Support.CODE_TO_ERRORS.get(code);
    }

    /**
     * @return unique {@code String} which can be used to uniquely identify the error
     */
    String getApiErrorCode();

    /**
     * @return human-readable title of the error (i.e. short summary)
     */
    String getTitle();

}

class Support {
    /**
     * CODE_TO_ERRORS - contains all the error codes in use mapped by the error code, the {@link #init()}  also ensures
     * that no duplicate error codes can be inserted
     */
    static final Map<String, ApiErrorDetail> CODE_TO_ERRORS = init();

    private static Map<String, ApiErrorDetail> init() {
        var errorList = ImmutableList.<ApiErrorDetail>builder()
                .add(SystemAPIError.values())
                .add(UserAPIError.values())
                .add(SupportAPIError.values())
                .add(TrackingAPIError.values())
                .build();

        var resultBuilder = ImmutableMap.<String, ApiErrorDetail>builder();

        for (var errorDetail : errorList) {
            resultBuilder.put(errorDetail.getApiErrorCode(), errorDetail);
        }

        return resultBuilder.buildOrThrow();
    }
}
