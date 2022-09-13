package com.github.szilex94.edu.round_tracker.service.user;

import com.github.szilex94.edu.round_tracker.mappers.UserMapper;
import com.github.szilex94.edu.round_tracker.repository.user.UserDao;
import com.github.szilex94.edu.round_tracker.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserMapper mapper, UserRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }


    @Override
    public Mono<User> createNewUser(User user) {
        UserDao dao = this.mapper.toDao(user);
        return repository.save(dao)
                .map(mapper::fromDao);
    }

    @Override
    public Mono<User> retrieveById(String userId) {
        checkArgument(!isNullOrEmpty(userId), "Null or empty userID not allowed!");
        return repository.findById(userId)
                .map(mapper::fromDao);
    }

    @Override
    public Flux<User> getUsers() {
        return repository.findAll()
                .map(mapper::fromDao);
    }
}
