package com.github.szilex94.edu.round_tracker.rest.user.error;

import com.github.szilex94.edu.round_tracker.repository.exception.DuplicateUserProfileException;
import com.github.szilex94.edu.round_tracker.rest.error.ProblemDetailFactory;
import com.github.szilex94.edu.round_tracker.rest.error.codes.UserAPIError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserApiErrorHandler extends ResponseEntityExceptionHandler {

    private final ProblemDetailFactory detailFactory;

    public UserApiErrorHandler(ProblemDetailFactory detailFactory) {
        this.detailFactory = detailFactory;
    }

    @ExceptionHandler(value = DuplicateUserProfileException.class)
    protected ProblemDetail handleDuplicateUserProfile(DuplicateUserProfileException ex) {

        var result = detailFactory.createAndInitiate(HttpStatus.CONFLICT, UserAPIError.USER_PROFILE_UNIQUE_IDENTIFIER_CONFLICT);
        result.setDetail(ex.getMessage());

        return result;
    }

}
