package com.github.szilex94.edu.round_tracker.service.support.caliber;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.UnaryOperator;

public interface CaliberDefinitionService {

    /**
     * @return all the stored definitions in the DB
     */
    Flux<CaliberTypeDefinition> retrieveCaliberDefinitions();

    /**
     * Saves the supplied caliber in the DB
     *
     * @param caliberDef the desired definition
     * @return a definition representation which was saved in the db
     */
    Mono<CaliberTypeDefinition> createNewCaliberDefinition(CaliberTypeDefinition caliberDef);

    /**
     * Retrieves the existing caliber definition (if it exists) and applies the supplied unary operator upon it.
     * If no corresponding definition exists no action will be performed (or exception thrown).
     *
     * @param code         the caliber code used to uniquely identify the entity
     * @param withExisting unary operator which will be called with the existing entity serving as the argument and the resulting entity being persisted in the DB
     * @return the updated definition
     * @throws IllegalStateException if the {@link CaliberTypeDefinition#getCode()} of the resulting definition does not match the supplied one
     */
    Mono<CaliberTypeDefinition> updateExisting(String code, UnaryOperator<CaliberTypeDefinition> withExisting);
}
