package com.github.szilex94.edu.round_tracker.repository.tracking.adapter;

import com.github.szilex94.edu.round_tracker.repository.tracking.TrackingRepository;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChangeLog;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TrackingRepositoryAdapterImpl implements TrackingRepositoryAdapter {

    private final TrackingRepository repository;

    public TrackingRepositoryAdapterImpl(TrackingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<AmmunitionChangeLog> logAmmunitionChange(AmmunitionChange change) {
        throw new UnsupportedOperationException("TBD");
    }
}
