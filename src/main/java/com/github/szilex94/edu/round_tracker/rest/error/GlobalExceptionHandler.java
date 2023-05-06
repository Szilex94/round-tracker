package com.github.szilex94.edu.round_tracker.rest.error;

import com.github.szilex94.edu.round_tracker.rest.error.codes.SystemAPIError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ProblemDetailFactory pdFactory;

    public GlobalExceptionHandler(ProblemDetailFactory pdFactory) {
        this.pdFactory = pdFactory;
    }

    @ExceptionHandler(value = UnsupportedOperationException.class)
    protected ProblemDetail handleUnsupportedAPICall(RuntimeException ex) {
        var result = pdFactory.createAndInitiate(HttpStatus.NOT_IMPLEMENTED, SystemAPIError.SYSTEM_API_NOT_SUPPORTED);
        result.setDetail(ex.getMessage());
        return result;
    }

}
