package com.github.szilex94.edu.round_tracker.repository.user;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends ReactiveMongoRepository<UserProfileDao, String> {

}
