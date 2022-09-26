package com.github.szilex94.edu.round_tracker.rest.user.profile;

import com.github.szilex94.edu.round_tracker.service.user.profile.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserProfileDtoMapper {

    UserProfile fromDto(UserProfileDto dto);

    UserProfileDto toDto(UserProfile user);

    UserProfile fromPathDto(PatchUserProfileDto patchUserProfileDto);
}
