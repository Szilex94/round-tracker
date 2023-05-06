package com.github.szilex94.edu.round_tracker.rest.support.error;

import com.github.szilex94.edu.round_tracker.rest.error.ProblemDetailFactory;
import com.github.szilex94.edu.round_tracker.rest.error.codes.SupportAPIError;
import com.github.szilex94.edu.round_tracker.service.support.caliber.DuplicateCaliberCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SupportApiErrorHandler extends ResponseEntityExceptionHandler {

    private final ProblemDetailFactory detailFactory;

    public SupportApiErrorHandler(ProblemDetailFactory detailFactory) {
        this.detailFactory = detailFactory;
    }

    @ExceptionHandler(value = DuplicateCaliberCodeException.class)
    protected ProblemDetail handleDuplicateCaliberCode(DuplicateCaliberCodeException ex) {

        var result = detailFactory.createAndInitiate(HttpStatus.CONFLICT, SupportAPIError.CALIBER_DEFINITION_CODE_CONFLICT);
        result.setDetail(ex.getMessage());

        return result;
    }
}
