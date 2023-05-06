package com.github.szilex94.edu.round_tracker.rest.error.codes;

/**
 * Enumeration encompassing errors encountered while tracking ammunition change
 *
 * @author szilex94
 */
public enum TrackingAPIError implements ApiErrorDetail {
    UNKNOWN_AMMUNITION_CODE("ac-0000", "Unknown ammunition code");

    private final String apiErrorCode;

    private final String title;

    TrackingAPIError(String apiErrorCode, String title) {
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
