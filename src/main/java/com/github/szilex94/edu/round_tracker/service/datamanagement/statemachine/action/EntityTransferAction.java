package com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.action;

import com.github.szilex94.edu.round_tracker.repository.tracking.repository.ArchiveTransferFailedException;
import com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.event.DataFlowEvent;
import com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.state.DataManagementState;
import com.github.szilex94.edu.round_tracker.service.tracking.AmmunitionExpenseArchivingService;
import com.github.szilex94.edu.round_tracker.service.tracking.AmmunitionExpenseTrackingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class EntityTransferAction implements ArchivingAction {

    private final AmmunitionExpenseArchivingService archivingService;

    public EntityTransferAction(AmmunitionExpenseTrackingService trackingService) {
        this.archivingService = trackingService;
    }

    @Override
    public Mono<Void> apply(StateContext<DataManagementState, DataFlowEvent> dataManagementStateDataFlowEventStateContext) {
        return archivingService.transferMarkedEntities()
                .onErrorResume(this::logErrors)
                .count()
                .doOnNext(transferredCount -> log.info("Successfully transferred {} entities!", transferredCount))
                .then();
    }

    private <T> Mono<T> logErrors(Throwable e) {
        if (e instanceof ArchiveTransferFailedException ex) {
            log.error("Failed to process transfer for element {}", ex.getId(), ex);
        } else {
            log.error("An unforeseen exception occurred while transferring archived element", e);
        }

        return Mono.empty();
    }
}
