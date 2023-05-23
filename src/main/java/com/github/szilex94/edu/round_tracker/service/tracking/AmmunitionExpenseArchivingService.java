package com.github.szilex94.edu.round_tracker.service.tracking;

import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface AmmunitionExpenseArchivingService {

    /**
     * Traverses the DB and marks entries for archiving
     *
     * @param cutOff non-null time stamp, all entries before this time stamp will be marked for archiving
     * @return the number of entries which where marked
     */
    Mono<Long> markEntriesForArchiving(LocalDate cutOff);

    /**
     * Transfers entities which where marked by {@link #markEntriesForArchiving(LocalDate)} into a long term storage
     *
     * @return a flux of each entity which was transferred
     */
    Flux<AmmunitionChange> transferMarkedEntities();

    /**
     * Removes entities which where successfully archived in {@link #transferMarkedEntities()}
     *
     * @return the number of elements which where removed
     */
    Mono<Long> removeArchivedEntities();
}
