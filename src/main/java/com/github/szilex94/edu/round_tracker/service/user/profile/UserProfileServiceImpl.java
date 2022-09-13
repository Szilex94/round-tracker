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
    private final UserProfileRepository repository;

    @Autowired
    public UserProfileServiceImpl(UserMapper mapper, UserProfileRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }


    @Override
    public Mono<User> createNewUser(User user) {
        UserProfileDao dao = this.mapper.toDao(user);
        return repository.save(dao)
                .map(mapper::fromDao);
    }

    @Override
    public Mono<User> retrieveById(String userId) {
        checkArgument(!isNullOrEmpty(userId), "Null or empty userID not allowed!");
        return repository.findById(userId)
                .map(mapper::fromDao);
    }

}
