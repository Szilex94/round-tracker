package com.github.szilex94.edu.round_tracker.service.tracking;

import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChangeSummary;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

/**
 * Records changes to a users change in available ammunition.
 *
 * @author szilex94
 */
public interface AmmunitionExpenseTrackingService {

    /**
     * @param change - represents the change which occurred to a users disposable ammunition count
     * @return a summary containing the current ammunition count
     */
    Mono<AmmunitionChangeSummary> recordAmmunitionChange(AmmunitionChange change);

    /**
     * Traverses the DB and marks entries for archiving
     *
     * @param cutOff non-null time stamp, all entries before this time stamp will be marked for archiving
     * @return the number of entries which where marked
     */
    Mono<Long> markEntriesForArchiving(LocalDate cutOff);
}
