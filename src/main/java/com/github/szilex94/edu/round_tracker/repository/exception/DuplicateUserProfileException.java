package com.github.szilex94.edu.round_tracker.repository.exception;

/**
 * Raised in situations when we encounter duplicates when it comes to the fields which are uniqueness identify a user`s profile
 */
public class DuplicateUserProfileException extends RuntimeException {

    public DuplicateUserProfileException(Throwable cause) {
        super("The supplied user profile conflicts with an existing one!", cause);
    }
}
