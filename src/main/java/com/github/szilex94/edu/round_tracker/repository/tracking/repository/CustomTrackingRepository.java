package com.github.szilex94.edu.round_tracker.repository.tracking.repository;

import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionChangeLogDao;
import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionSummaryDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

/**
 * Offer specialized queries which leverage functionalities offered by MongoDB
 *
 * @author szilex94
 */
public interface CustomTrackingRepository {

    Mono<AmmunitionSummaryDao> recordAmmunitionChange(AmmunitionChangeLogDao change);

    Mono<Long> markEntriesForArchiving(LocalDate cutoff);

    Flux<AmmunitionChangeLogDao> transferMarkedEntities();

    Mono<Long> removeArchivedEntities();
}
