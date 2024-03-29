package com.github.szilex94.edu.round_tracker.repository.tracking.adapter;

import com.github.szilex94.edu.round_tracker.repository.tracking.mapper.TrackingDaoMapper;
import com.github.szilex94.edu.round_tracker.repository.tracking.repository.TrackingRepository;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChangeSummary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

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
                .map(mapper::toAmmunitionChange);

    }

    @Override
    public Mono<Long> markEntriesForArchiving(LocalDate cutoff) {
        return repository.markEntriesForArchiving(cutoff);
    }

    @Override
    public Flux<AmmunitionChange> transferMarkedEntities() {
        return repository.transferMarkedEntities()
                .map(this.mapper::fromDao);
    }

    @Override
    public Mono<Long> removeArchivedEntities() {
        return repository.removeArchivedEntities();
    }
}
