package com.github.szilex94.edu.round_tracker.service.tracking;

import com.github.szilex94.edu.round_tracker.repository.tracking.adapter.TrackingRepositoryAdapter;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChangeSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@Slf4j
public class AmmunitionExpenseTrackingServiceImpl implements AmmunitionExpenseTrackingService {

    private final TrackingRepositoryAdapter repositoryAdapter;

    public AmmunitionExpenseTrackingServiceImpl(TrackingRepositoryAdapter repositoryAdapter) {
        this.repositoryAdapter = repositoryAdapter;
    }

    @Override
    public Mono<AmmunitionChangeSummary> recordAmmunitionChange(AmmunitionChange change) {
        log.debug("Received change request {}!", change);

        return repositoryAdapter.recordAmmunitionChange(change);
    }

    @Override
    public Mono<Void> markEntriesForArchiving(LocalDate cutOff) {
        throw new UnsupportedOperationException("Implement in service!");
    }
}
