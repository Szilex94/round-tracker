package com.github.szilex94.edu.round_tracker.service.user.profile;

import com.github.szilex94.edu.round_tracker.mappers.UserMapper;
import com.github.szilex94.edu.round_tracker.repository.user.profile.UserProfileDao;
import com.github.szilex94.edu.round_tracker.repository.user.profile.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserMapper mapper;


    private final ProfileIdGenerator idGenerator;
    private final UserProfileRepository repository;

    @Autowired
    public UserProfileServiceImpl(UserMapper mapper,
                                  ProfileIdGenerator idGenerator,
                                  UserProfileRepository repository) {
        this.mapper = mapper;
        this.idGenerator = idGenerator;
        this.repository = repository;
    }


    @Override
    public Mono<UserProfile> createNewUser(UserProfile user) {
        checkArgument(user != null, "Null input not allowed!");

        UserProfileDao dao = this.mapper.toDao(user);
        dao.setProfileId(idGenerator.apply(user));

        return repository.save(dao)
                .map(mapper::fromDao);
    }

    @Override
    public Mono<UserProfile> retrieveById(String userId) {
        checkArgument(!isNullOrEmpty(userId), "Null or empty userID not allowed!");
        return repository.findById(userId)
                .map(mapper::fromDao);
    }

}
