package com.github.szilex94.edu.round_tracker.repository.user.profile;

import com.github.szilex94.edu.round_tracker.service.user.profile.UserProfile;
import reactor.core.publisher.Mono;

/**
 * Adapter which wraps around DB implementation
 */
public interface UserProfileRepositoryAdapter {

    Mono<UserProfile> findById(String id);

}
