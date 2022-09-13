package com.github.szilex94.edu.round_tracker.mappers;

import com.github.szilex94.edu.round_tracker.repository.user.profile.UserProfileDao;
import com.github.szilex94.edu.round_tracker.rest.user.profile.UserProfileDto;
import com.github.szilex94.edu.round_tracker.service.user.profile.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserProfile fromDto(UserProfileDto dto);

    UserProfileDto toDto(UserProfile user);

    UserProfileDao toDao(UserProfile user);

    UserProfile fromDao(UserProfileDao dao);

}
