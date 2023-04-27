package com.github.szilex94.edu.round_tracker.repository.support.caliber;

import com.github.szilex94.edu.round_tracker.service.support.caliber.CaliberTypeDefinition;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CaliberDefinitionRepositoryAdapter {

    Mono<CaliberTypeDefinition> findByCode(String code);

    Flux<CaliberTypeDefinition> retrieveAll();

    Mono<CaliberTypeDefinition> save(CaliberTypeDefinition input);
}
