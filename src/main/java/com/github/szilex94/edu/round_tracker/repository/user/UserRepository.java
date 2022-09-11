package com.github.szilex94.edu.round_tracker.repository.user;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveMongoRepository<UserDao, UUID> {

    Mono<UserDao> findById(String id);

}
