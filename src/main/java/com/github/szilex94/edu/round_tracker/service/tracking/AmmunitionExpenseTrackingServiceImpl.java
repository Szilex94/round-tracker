package com.github.szilex94.edu.round_tracker.service.tracking;

import com.github.szilex94.edu.round_tracker.repository.tracking.adapter.TrackingRepositoryAdapter;
import com.github.szilex94.edu.round_tracker.service.support.caliber.CaliberDefinitionService;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import com.github.szilex94.edu.round_tracker.service.tracking.model.UnknownAmmunitionCodeException;
import com.github.szilex94.edu.round_tracker.service.tracking.model.UserAmmunitionSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AmmunitionExpenseTrackingServiceImpl implements AmmunitionExpenseTrackingService {

    private final CaliberDefinitionService caliberService;

    private final TrackingRepositoryAdapter repositoryAdapter;

    public AmmunitionExpenseTrackingServiceImpl(CaliberDefinitionService caliberService,
                                                TrackingRepositoryAdapter repositoryAdapter) {
        this.caliberService = caliberService;
        this.repositoryAdapter = repositoryAdapter;
    }

    @Override
    public Mono<UserAmmunitionSummary> recordAmmunitionChange(AmmunitionChange change) {
        log.debug("Received change request {}!", change);

        return caliberService.findByCode(change.getAmmunitionCode())
                .switchIfEmpty(Mono.error(UnknownAmmunitionCodeException::new))
                .then(repositoryAdapter.recordAmmunitionChange(change))
                .map(logEntry -> UserAmmunitionSummary.builder()
                        .forUser(logEntry.getUserId())
                        .setGrandTotal(logEntry.getAmount())
                        .build());
    }
}
