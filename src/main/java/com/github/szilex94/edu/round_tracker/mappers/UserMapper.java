package com.github.szilex94.edu.round_tracker.mappers;

import com.github.szilex94.edu.round_tracker.repository.user.profile.UserProfileDao;
import com.github.szilex94.edu.round_tracker.rest.user.profile.UserProfileDto;
import com.github.szilex94.edu.round_tracker.service.user.profile.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromDto(UserProfileDto dto);

    UserProfileDto toDto(User user);

    UserProfileDao toDao(User user);

    User fromDao(UserProfileDao dao);

}
