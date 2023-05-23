package com.github.szilex94.edu.round_tracker.service.tracking;

import com.github.szilex94.edu.round_tracker.repository.tracking.adapter.TrackingRepositoryAdapter;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChangeSummary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class AmmunitionExpenseTrackingServiceImpl implements AmmunitionExpenseTrackingService {

    private final TrackingRepositoryAdapter repositoryAdapter;

    public AmmunitionExpenseTrackingServiceImpl(TrackingRepositoryAdapter repositoryAdapter) {
        this.repositoryAdapter = repositoryAdapter;
    }

    @Override
    public Mono<AmmunitionChangeSummary> recordAmmunitionChange(AmmunitionChange change) {
        return repositoryAdapter.recordAmmunitionChange(change);
    }

    @Override
    public Mono<Long> markEntriesForArchiving(LocalDate cutOff) {
        return repositoryAdapter.markEntriesForArchiving(cutOff);
    }

    @Override
    public Flux<AmmunitionChange> transferMarkedEntities() {
        return repositoryAdapter.transferMarkedEntities();
    }

    @Override
    public Mono<Long> removeArchivedEntities() {
        return repositoryAdapter.removeArchivedEntities();
    }
}
