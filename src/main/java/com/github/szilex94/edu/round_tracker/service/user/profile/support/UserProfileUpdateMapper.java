package com.github.szilex94.edu.round_tracker.service.user.profile.support;

import com.github.szilex94.edu.round_tracker.service.user.profile.UserProfile;

/**
 * Specialized mapper implementation which can update non-null fields from a source entity
 *
 * @author szilex94
 */
public interface UserProfileUpdateMapper {


    UserProfile updateNonNull(UserProfile source, UserProfile target);
}
