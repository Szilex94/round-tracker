package com.github.szilex94.edu.round_tracker.rest.error;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

/**
 * A generic error response returned by the REST-API in exceptional situations
 */
@Data
@Accessors(chain = true)
public final class GenericErrorResponse {

    private OffsetDateTime occurred;

    private String apiErrorCode;

    private String message;

}
