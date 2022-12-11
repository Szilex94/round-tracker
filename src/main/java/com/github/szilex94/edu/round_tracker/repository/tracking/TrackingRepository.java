package com.github.szilex94.edu.round_tracker.repository.tracking;

import com.github.szilex94.edu.round_tracker.repository.tracking.dao.AmmunitionChangeLogDao;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackingRepository extends ReactiveMongoRepository<AmmunitionChangeLogDao, String> {
}
