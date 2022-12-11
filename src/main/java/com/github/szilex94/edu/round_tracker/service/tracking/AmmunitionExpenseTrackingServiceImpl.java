package com.github.szilex94.edu.round_tracker.service.tracking;

import com.github.szilex94.edu.round_tracker.repository.tracking.adapter.TrackingRepositoryAdapter;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import com.github.szilex94.edu.round_tracker.service.tracking.model.UserAmmunitionSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionType.NINE_MILLIMETER;

@Service
@Slf4j
public class AmmunitionExpenseTrackingServiceImpl implements AmmunitionExpenseTrackingService {

    private final TrackingRepositoryAdapter repositoryAdapter;

    public AmmunitionExpenseTrackingServiceImpl(TrackingRepositoryAdapter repositoryAdapter) {
        this.repositoryAdapter = repositoryAdapter;
    }

    @Override
    public Mono<UserAmmunitionSummary> recordAmmunitionChange(AmmunitionChange change) {
        log.debug("Received change request {}!", change);

        return repositoryAdapter.logAmmunitionChange(change)
                .map(logEntry -> UserAmmunitionSummary.builder()
                        .forUser(logEntry.getUserId())
                        .addSummary(NINE_MILLIMETER, logEntry.getAmount())
                        .build());

    }
}
