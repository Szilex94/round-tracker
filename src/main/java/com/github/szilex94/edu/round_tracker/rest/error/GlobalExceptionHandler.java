package com.github.szilex94.edu.round_tracker.rest.error;

import com.github.szilex94.edu.round_tracker.repository.exception.DuplicateUserProfileException;
import com.github.szilex94.edu.round_tracker.service.support.caliber.DuplicateCaliberCodeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

import static com.github.szilex94.edu.round_tracker.rest.error.ApiErrorCode.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = DuplicateUserProfileException.class)
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex,
            WebRequest request) {


        var body = new GenericErrorResponse()
                .setApiErrorCode(USER_PROFILE_UNIQUE_IDENTIFIER_CONFLICT.getCode())
                .setOccurred(OffsetDateTime.now())
                .setMessage(ex.getMessage());

        return handleExceptionInternal(ex,
                body,
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                request);
    }

    @ExceptionHandler(value = DuplicateCaliberCodeException.class)
    protected ResponseEntity<Object> handleDuplicateCaliberCode(
            RuntimeException ex,
            WebRequest request) {


        var body = new GenericErrorResponse()
                .setApiErrorCode(CALIBER_DEFINITION_CODE_CONFLICT.getCode())
                .setOccurred(OffsetDateTime.now())
                .setMessage(ex.getMessage());

        return handleExceptionInternal(ex,
                body,
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                request);
    }

    @ExceptionHandler(value = UnsupportedOperationException.class)
    protected ResponseEntity<Object> handleUnsupportedAPICall(RuntimeException ex,
                                                              WebRequest request) {
        var body = new GenericErrorResponse()
                .setApiErrorCode(SYSTEM_API_NOT_SUPPORTED.getCode())
                .setMessage(ex.getMessage())
                .setOccurred(OffsetDateTime.now());

        return handleExceptionInternal(ex,
                body,
                new HttpHeaders(),
                HttpStatus.NOT_IMPLEMENTED,
                request);
    }

}
