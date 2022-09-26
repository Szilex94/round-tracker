package com.github.szilex94.edu.round_tracker.service.user.profile;

import com.github.szilex94.edu.round_tracker.repository.user.profile.UserProfileRepositoryAdapter;
import com.github.szilex94.edu.round_tracker.service.user.profile.support.UserProfileUpdateMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepositoryAdapter repositoryAdapter;

    private final UserProfileUpdateMapper updateMapper;

    public UserProfileServiceImpl(UserProfileRepositoryAdapter repositoryAdapter,
                                  UserProfileUpdateMapper updateMapper) {
        this.repositoryAdapter = repositoryAdapter;
        this.updateMapper = updateMapper;
    }


    @Override
    public Mono<UserProfile> createNewUser(UserProfile user) {
        return repositoryAdapter.createOrUpdate(user);
    }

    @Override
    public Mono<UserProfile> retrieveById(String userId) {
        return repositoryAdapter.findById(userId);
    }

    @Override
    public Mono<UserProfile> updateUserProfile(UserProfile reference) {
        checkArgument(reference != null, "Null input not allowed!");
        var id = reference.getId();
        checkArgument(!isNullOrEmpty(id), "Null or empty reference id not allowed!");

        return repositoryAdapter.findById(id)
                .map(db -> updateMapper.updateNonNull(reference, db))
                .flatMap(repositoryAdapter::createOrUpdate);
    }

}
