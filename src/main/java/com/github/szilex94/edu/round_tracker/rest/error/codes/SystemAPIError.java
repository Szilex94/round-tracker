package com.github.szilex94.edu.round_tracker.rest.error.codes;

/**
 * Enumeration encompassing errors which are related to the application (unforeseen exceptions)
 *
 * @author szilex94
 */
public enum SystemAPIError implements ApiErrorDetail {
    SYSTEM_API_NOT_SUPPORTED("sys-0000", "Unimplemented API Call");

    private final String apiErrorCode;

    private final String title;

    SystemAPIError(String apiErrorCode, String title) {
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
