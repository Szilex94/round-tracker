package com.github.szilex94.edu.round_tracker.service.user;

import reactor.core.publisher.Mono;

/**
 * Manges objects which represent persons that are managed by the application
 *
 * @author szilex94
 */
public interface UserService {

    /**
     * Creates a new user
     *
     * @param user
     * @return the user which was persisted
     */
    Mono<User> createNewUser(User user);
}
