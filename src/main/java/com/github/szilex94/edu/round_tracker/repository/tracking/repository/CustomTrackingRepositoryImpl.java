package com.github.szilex94.edu.round_tracker.repository.tracking.repository;

import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionChangeLogDao;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Mono;

public class CustomTrackingRepositoryImpl implements CustomTrackingRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public CustomTrackingRepositoryImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<Void> recordAmmunitionChange(AmmunitionChangeLogDao change) {
        throw new UnsupportedOperationException("TBD");
    }
}
