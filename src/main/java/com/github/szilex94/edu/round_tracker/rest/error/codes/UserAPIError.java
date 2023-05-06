package com.github.szilex94.edu.round_tracker.rest.error.codes;

/**
 * Enumeration containing errors encountered while managing users
 *
 * @author szilex94
 */
public enum UserAPIError implements ApiErrorDetail {
    USER_PROFILE_UNIQUE_IDENTIFIER_CONFLICT("up-0000", "User Id already in use");

    private final String apiErrorCode;

    private final String title;

    UserAPIError(String apiErrorCode, String title) {
        this.apiErrorCode = apiErrorCode;
        this.title = title;
    }

    @Override
    public String getApiErrorCode() {
        return this.apiErrorCode;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

}
