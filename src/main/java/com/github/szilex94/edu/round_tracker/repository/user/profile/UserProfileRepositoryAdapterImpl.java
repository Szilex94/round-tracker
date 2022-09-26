package com.github.szilex94.edu.round_tracker.repository.user.profile;

import com.github.szilex94.edu.round_tracker.service.user.profile.UserProfile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserProfileRepositoryAdapterImpl implements UserProfileRepositoryAdapter {

    private final UserProfileDaoMapper mapper;

    private final UserProfileRepository userProfileRepository;


    public UserProfileRepositoryAdapterImpl(UserProfileDaoMapper mapper, UserProfileRepository userProfileRepository) {
        this.mapper = mapper;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public Mono<UserProfile> findById(String id) {
        return userProfileRepository.findById(id)
                .map(mapper::fromDao);
    }

}
