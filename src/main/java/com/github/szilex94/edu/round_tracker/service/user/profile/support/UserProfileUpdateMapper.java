package com.github.szilex94.edu.round_tracker.service.user.profile.support;

import com.github.szilex94.edu.round_tracker.service.user.profile.UserProfile;
import org.mapstruct.*;

/**
 * Specialized mapper implementation which can update non-null fields from a source entity
 *
 * @author szilex94
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserProfileUpdateMapper {


    @Mapping(target = "id", ignore = true)
    UserProfile updateNonNull(UserProfile source, @MappingTarget UserProfile target);
}
