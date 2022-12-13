package com.github.szilex94.edu.round_tracker.repository.tracking.repository;

import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionChangeLogDao;
import reactor.core.publisher.Mono;

/**
 * Offer specialized queries which leverage functionalities offered by MongoDB
 *
 * @author szilex94
 */
public interface CustomTrackingRepository {

    Mono<Void> recordAmmunitionChange(AmmunitionChangeLogDao change);
}
