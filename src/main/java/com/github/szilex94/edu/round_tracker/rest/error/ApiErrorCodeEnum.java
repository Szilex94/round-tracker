package com.github.szilex94.edu.round_tracker.rest.error;

/**
 * An enumeration encompassing the error codes returned by the rest API
 *
 * @deprecated will be replaced by dedicated enums for each API
 */
@Deprecated
public enum ApiErrorCodeEnum {
    @Deprecated
    SYSTEM_API_NOT_SUPPORTED("sys-0000"),
    @Deprecated
    CALIBER_DEFINITION_CODE_CONFLICT("cd-0000"),
    @Deprecated
    UNKNOWN_AMMUNITION_CODE("ac-0000"),
    @Deprecated
    USER_PROFILE_UNIQUE_IDENTIFIER_CONFLICT("up-0000");

    private final String code;

    ApiErrorCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
