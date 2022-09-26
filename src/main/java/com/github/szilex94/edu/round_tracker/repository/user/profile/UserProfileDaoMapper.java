package com.github.szilex94.edu.round_tracker.repository.user.profile;

import com.github.szilex94.edu.round_tracker.service.user.profile.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserProfileDaoMapper {

    UserProfile fromDao(UserProfileDao dao);

    UserProfileDao toDao(UserProfile user);
}
