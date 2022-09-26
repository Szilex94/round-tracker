package com.github.szilex94.edu.round_tracker.service.user.profile;

import com.github.szilex94.edu.round_tracker.mappers.UserProfileMapper;
import com.github.szilex94.edu.round_tracker.repository.user.profile.UserProfileDao;
import com.github.szilex94.edu.round_tracker.repository.user.profile.UserProfileRepository;
import com.github.szilex94.edu.round_tracker.repository.user.profile.UserProfileRepositoryAdapter;
import com.github.szilex94.edu.round_tracker.service.user.profile.support.UserProfileUpdateMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Deprecated
    private final UserProfileMapper mapper;

    /**
     * @deprecated - this functionality will be extracted into a DB adapter
     */
    @Deprecated
    private final UserProfileRepository repository;

    private final UserProfileRepositoryAdapter repositoryAdapter;

    private final UserProfileUpdateMapper updateMapper;

    public UserProfileServiceImpl(UserProfileRepositoryAdapter repositoryAdapter,
                                  UserProfileMapper mapper,
                                  UserProfileRepository repository,
                                  UserProfileUpdateMapper updateMapper) {
        this.repositoryAdapter = repositoryAdapter;
        this.mapper = mapper;
        this.repository = repository;
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

        return repository.findById(id)
                .map(db -> updateUserProfile(db, reference))
                .flatMap(repository::save)
                .map(mapper::fromDao);
    }

    private UserProfileDao updateUserProfile(UserProfileDao dbResult, UserProfile desired) {
        var inDB = mapper.fromDao(dbResult);
        updateMapper.updateNonNull(desired, inDB);
        return mapper.toDao(inDB);
    }

}
