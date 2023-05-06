package com.github.szilex94.edu.round_tracker.rest.error.codes;

/**
 * Intended to be implemented by enumerations which detail anticipated problems.
 *
 * @author szilex94
 */
public sealed interface ApiErrorDetail permits SystemAPIError, UserAPIError {

    /**
     * API_ERROR_CODE - string which should be used as a field name in error response to denote the error code
     */
    String API_ERROR_CODE = "apiErrorCode";

    /**
     * @return unique {@code String} which can be used to uniquely identify the error
     */
    String getApiErrorCode();

    /**
     * @return human-readable title of the error (i.e. short summary)
     */
    String getTitle();

    /**
     * @return human-readable description (i.e. long summary)
     */
    String getDetail();


}
