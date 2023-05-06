package com.github.szilex94.edu.round_tracker.rest.error.codes;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

public enum SystemAPIError implements ApiErrorDetail {
    SYSTEM_API_NOT_SUPPORTED("sys-0000", "Unimplemented API Call");

    private final String apiErrorCode;

    private final String title;

    private static final Map<String, SystemAPIError> CODE_TO_MEMBER = Arrays.stream(SystemAPIError.values())
            .collect(Collectors.toMap(SystemAPIError::getApiErrorCode, identity()));// This collector rejects duplicate keys

    SystemAPIError(String apiErrorCode, String title) {
        this.apiErrorCode = apiErrorCode;
        this.title = title;
    }

    public static SystemAPIError findByApiErrorCode(String apiErrorCode) {
        return CODE_TO_MEMBER.get(apiErrorCode);
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
