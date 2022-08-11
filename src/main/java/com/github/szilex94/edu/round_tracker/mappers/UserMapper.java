package com.github.szilex94.edu.round_tracker.mappers;

import com.github.szilex94.edu.round_tracker.repository.user.UserDao;
import com.github.szilex94.edu.round_tracker.rest.user.UserDto;
import com.github.szilex94.edu.round_tracker.service.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromDto(UserDto dto);

    UserDto toDto(User user);

    UserDao toDao(User user);

    User fromDao(UserDao dao);

}
