package com.github.szilex94.edu.round_tracker.rest.error.codes;

/**
 * Contains errors which can occur in the components which support the system
 *
 * @author szilex94
 */
public enum SupportAPIError implements ApiErrorDetail {
    CALIBER_DEFINITION_CODE_CONFLICT("cd-0000", "Caliber definition already exists");

    private final String apiErrorCode;

    private final String title;

    SupportAPIError(String apiErrorCode, String title) {
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
