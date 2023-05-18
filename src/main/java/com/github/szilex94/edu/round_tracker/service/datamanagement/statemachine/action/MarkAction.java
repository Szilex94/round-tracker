package com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.action;

import com.github.szilex94.edu.round_tracker.service.datamanagement.archiving.time.ArchivingTimeSupplier;
import com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.event.DataFlowEvent;
import com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.state.DataManagementState;
import com.github.szilex94.edu.round_tracker.service.tracking.AmmunitionExpenseTrackingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Marks entries which are scheduled for archiving
 *
 * @author szilex94
 */
@Component
@Slf4j
public final class MarkAction implements ArchivingAction {

    private final ArchivingTimeSupplier timeSupplier;

    private final AmmunitionExpenseTrackingService trackingService;

    public MarkAction(ArchivingTimeSupplier timeSupplier, AmmunitionExpenseTrackingService trackingService) {
        this.timeSupplier = timeSupplier;
        this.trackingService = trackingService;
    }

    @Override
    public Mono<Void> apply(StateContext<DataManagementState, DataFlowEvent> dataManagementStateDataManagementEventStateContext) {
        return Mono.fromSupplier(timeSupplier)
                .doOnNext(time -> log.info("Proceeding to mark entries created before: {} for archiving!", time))
                .flatMap(trackingService::markEntriesForArchiving)
                .doOnSuccess(updatedCount -> log.info("Successfully marked {} entries for archiving!", updatedCount))
                .doOnError(this::onError)
                .then();
    }

    private void onError(Throwable throwable) {
        log.error("Failed to carry out mark action!", throwable);
    }
}
