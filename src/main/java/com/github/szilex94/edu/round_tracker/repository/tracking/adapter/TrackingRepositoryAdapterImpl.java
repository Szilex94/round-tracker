package com.github.szilex94.edu.round_tracker.repository.tracking.adapter;

import com.github.szilex94.edu.round_tracker.repository.tracking.mapper.TrackingDaoMapper;
import com.github.szilex94.edu.round_tracker.repository.tracking.repository.TrackingRepository;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChangeSummary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TrackingRepositoryAdapterImpl implements TrackingRepositoryAdapter {

    private final TrackingRepository repository;

    private final TrackingDaoMapper mapper;

    public TrackingRepositoryAdapterImpl(TrackingRepository repository, TrackingDaoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<AmmunitionChangeSummary> recordAmmunitionChange(AmmunitionChange change) {
        var newEntry = mapper.fromAmmunitionChange(change);

        return repository.recordAmmunitionChange(newEntry)
                .then(Mono.just(AmmunitionChangeSummary.builder().build()));

    }
}
