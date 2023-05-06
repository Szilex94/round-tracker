package com.github.szilex94.edu.round_tracker.rest.user.error;

import com.github.szilex94.edu.round_tracker.repository.exception.DuplicateUserProfileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class UserApiErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = DuplicateUserProfileException.class)
    protected ProblemDetail handleDuplicateUserProfile(DuplicateUserProfileException ex) {

        ProblemDetail result = ProblemDetail.forStatus(HttpStatus.CONFLICT);

        //TODO create a github wiki page for errors?
        result.setType(URI.create("https://github.com/Szilex94/round-tracker/wiki/User-API"));
        result.setTitle("Duplicate User Id");
        result.setDetail(ex.getMessage());

        return result;
    }

}
