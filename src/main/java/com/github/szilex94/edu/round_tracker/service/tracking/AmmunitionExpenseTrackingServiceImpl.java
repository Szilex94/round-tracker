package com.github.szilex94.edu.round_tracker.service.tracking;

import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import com.github.szilex94.edu.round_tracker.service.tracking.model.UserAmmunitionSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionType.NINE_MILLIMETER;

@Service
@Slf4j
public class AmmunitionExpenseTrackingServiceImpl implements AmmunitionExpenseTrackingService {
    @Override
    public Mono<UserAmmunitionSummary> recordAmmunitionChange(AmmunitionChange change) {

        log.debug("Received change request {}!", change);

        return Mono.just(UserAmmunitionSummary.builder()
                .forUser(change.getUserId())
                .addSummary(NINE_MILLIMETER, change.getAmount())
                .build());
    }
}
