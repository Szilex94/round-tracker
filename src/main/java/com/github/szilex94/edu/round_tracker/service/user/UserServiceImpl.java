package com.github.szilex94.edu.round_tracker.service.user;

import com.github.szilex94.edu.round_tracker.mappers.UserMapper;
import com.github.szilex94.edu.round_tracker.repository.user.UserDao;
import com.github.szilex94.edu.round_tracker.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
}
