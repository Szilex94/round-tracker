package com.github.szilex94.edu.round_tracker.service.user.profile.support;

import com.github.szilex94.edu.round_tracker.service.user.profile.ProfileIdGenerator;
import com.github.szilex94.edu.round_tracker.service.user.profile.UserProfile;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class AbstractUserProfileUpdateMapper implements UserProfileUpdateMapper {

    @Autowired
    protected ProfileIdGenerator idGenerator;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profileId", ignore = true)
    @Override
    public abstract UserProfile updateNonNull(UserProfile source, @MappingTarget UserProfile target);

    @AfterMapping
    protected UserProfile updateProfileId(@MappingTarget UserProfile target) {
        return target.setProfileId(idGenerator.apply(target));
    }
}
