package com.github.szilex94.edu.round_tracker.rest.error.codes;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

/**
 * Enumeration containing errors encountered while managing users
 *
 * @author szilex94
 */
public enum UserAPIError implements ApiErrorDetail {
    USER_PROFILE_UNIQUE_IDENTIFIER_CONFLICT("up-0000",
            "User Id already in use",
            "The supplied User Id is already in use! Please offer a different one.");

    private final String apiErrorCode;

    private final String title;

    private final String details;

    private static final Map<String, UserAPIError> CODE_TO_MEMBER = Arrays.stream(UserAPIError.values())
            .collect(Collectors.toMap(UserAPIError::getApiErrorCode, identity()));// This collector rejects duplicate keys

    UserAPIError(String apiErrorCode, String title, String details) {
        this.apiErrorCode = apiErrorCode;
        this.title = title;
        this.details = details;
    }

    public static UserAPIError findByApiErrorCode(String apiErrorCode) {
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

    @Override
    public String getDetail() {
        return this.details;
    }
}
