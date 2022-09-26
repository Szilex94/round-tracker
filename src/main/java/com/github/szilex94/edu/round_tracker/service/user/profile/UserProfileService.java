package com.github.szilex94.edu.round_tracker.service.user.profile;

import reactor.core.publisher.Mono;

/**
 * Manges objects which represent persons that are managed by the application
 *
 * @author szilex94
 */
public interface UserProfileService {

    /**
     * Creates a new user
     *
     * @param user
     * @return the user which was persisted
     */
    Mono<UserProfile> createNewUser(UserProfile user);

    /**
     * @param userId uuid created when the user was created
     * @return the user associated with this id
     * @throws IllegalArgumentException for null or empty input
     */
    Mono<UserProfile> retrieveById(String userId);

    /**
     * @param reference - reference object from which non-null fields will be applied
     * @return the resulting object
     */
    Mono<UserProfile> updateUserProfile(UserProfile reference);

}
