package com.github.szilex94.edu.round_tracker.mappers;

import com.github.szilex94.edu.round_tracker.repository.user.profile.UserProfileDao;
import com.github.szilex94.edu.round_tracker.rest.user.profile.UserDto;
import com.github.szilex94.edu.round_tracker.service.user.profile.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromDto(UserDto dto);

    UserDto toDto(User user);

    UserProfileDao toDao(User user);

    User fromDao(UserProfileDao dao);

}
