package com.github.szilex94.edu.round_tracker.repository.user.profile;

import com.github.szilex94.edu.round_tracker.repository.exception.DuplicateUserProfileException;
import com.github.szilex94.edu.round_tracker.service.user.profile.UserProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

@Component
@Slf4j
public class UserProfileRepositoryAdapterImpl implements UserProfileRepositoryAdapter {

    private final UserProfileDaoMapper mapper;

    private final UserProfileRepository userProfileRepository;


    public UserProfileRepositoryAdapterImpl(UserProfileDaoMapper mapper, UserProfileRepository userProfileRepository) {
        this.mapper = mapper;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public Mono<UserProfile> findById(String id) {
        checkArgument(!isNullOrEmpty(id), "Null or empty ID not allowed!");
        return userProfileRepository.findById(id)
                .map(mapper::fromDao);
    }

    @Override
    public Mono<UserProfile> createOrUpdate(UserProfile userProfile) {
        checkArgument(userProfile != null, "Null input not allowed!");
        UserProfileDao dao = mapper.toDao(userProfile);
        return userProfileRepository.save(dao)
                .onErrorMap(this::handleWriteExceptions)
                .map(mapper::fromDao);
    }

    private Throwable handleWriteExceptions(Throwable throwable) {
        if (throwable instanceof DuplicateKeyException duplicateKeyException) {
            return new DuplicateUserProfileException(duplicateKeyException);
        }
        log.error("Encountered an unknown error while persisting user-profile to DB!", throwable);
        return throwable;
    }

}
