package com.github.szilex94.edu.round_tracker.service.tracking;

import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import com.github.szilex94.edu.round_tracker.service.tracking.model.UserAmmunitionSummary;
import org.jetbrains.annotations.TestOnly;
import reactor.core.publisher.Mono;

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
    @TestOnly
    Mono<UserAmmunitionSummary> recordAmmunitionChange(AmmunitionChange change);
}
