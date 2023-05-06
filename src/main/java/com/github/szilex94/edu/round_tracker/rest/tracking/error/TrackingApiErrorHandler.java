package com.github.szilex94.edu.round_tracker.rest.tracking.error;

import com.github.szilex94.edu.round_tracker.rest.error.ProblemDetailFactory;
import com.github.szilex94.edu.round_tracker.rest.error.codes.TrackingAPIError;
import com.github.szilex94.edu.round_tracker.service.tracking.model.UnknownAmmunitionCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TrackingApiErrorHandler extends ResponseEntityExceptionHandler {

    private final ProblemDetailFactory detailFactory;

    public TrackingApiErrorHandler(ProblemDetailFactory detailFactory) {
        this.detailFactory = detailFactory;
    }

    @ExceptionHandler(value = UnknownAmmunitionCodeException.class)
    protected ProblemDetail handleUnknownAmmunitionCode(UnknownAmmunitionCodeException ex) {


        var result = detailFactory.createAndInitiate(HttpStatus.BAD_REQUEST, TrackingAPIError.UNKNOWN_AMMUNITION_CODE);
        result.setDetail(ex.getMessage());

        return result;
    }
}
