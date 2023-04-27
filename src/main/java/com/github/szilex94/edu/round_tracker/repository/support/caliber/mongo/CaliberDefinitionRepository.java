package com.github.szilex94.edu.round_tracker.repository.support.caliber.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CaliberDefinitionRepository extends ReactiveMongoRepository<CaliberTypeDefinitionDao, String> {

    Mono<CaliberTypeDefinitionDao> findByCode(String code);
}