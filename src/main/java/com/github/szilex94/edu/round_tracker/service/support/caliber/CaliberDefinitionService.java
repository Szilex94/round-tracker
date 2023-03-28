package com.github.szilex94.edu.round_tracker.service.support.caliber;

import reactor.core.publisher.Mono;

public interface CaliberDefinitionService {

    /**
     * Saves the supplied caliber in the DB
     *
     * @param caliberDef
     * @return a definition representation which was saved in the db
     */
    Mono<CaliberTypeDefinition> createNewCaliberDefinition(CaliberTypeDefinition caliberDef);
}
