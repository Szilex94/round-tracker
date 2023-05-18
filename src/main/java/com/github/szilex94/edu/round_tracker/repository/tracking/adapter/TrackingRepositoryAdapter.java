package com.github.szilex94.edu.round_tracker.repository.tracking.adapter;

import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChangeSummary;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

/**
 * Handles persistence of changes relating to ammunition
 *
 * @author szilex94
 */
public interface TrackingRepositoryAdapter {

    /**
     * Persists the supplied change as a "log" entry (similar to a transaction)
     *
     * @param change the change which should be logged
     * @return the log entry created for the supplied change
     */
    Mono<AmmunitionChangeSummary> recordAmmunitionChange(AmmunitionChange change);

    /**
     * Marks all entries which are before the supplied cutoff for archiving
     *
     * @param cutoff not null
     * @return a mono containing the updated count
     */
    Mono<Long> markEntriesForArchiving(LocalDate cutoff);
}
