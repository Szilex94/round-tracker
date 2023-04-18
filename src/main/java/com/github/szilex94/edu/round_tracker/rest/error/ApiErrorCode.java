package com.github.szilex94.edu.round_tracker.rest.error;

/**
 * An enumeration encompassing the error codes returned by the rest API
 */
public enum ApiErrorCode {
    SYSTEM_API_NOT_SUPPORTED("sys-0000"),
    CALIBER_DEFINITION_CODE_CONFLICT("cd-0000"),
    USER_PROFILE_UNIQUE_IDENTIFIER_CONFLICT("up-0000");

    private final String code;

    ApiErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
