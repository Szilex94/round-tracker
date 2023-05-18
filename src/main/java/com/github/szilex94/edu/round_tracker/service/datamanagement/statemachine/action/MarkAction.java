package com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.action;

import com.github.szilex94.edu.round_tracker.service.datamanagement.archiving.time.ArchivingTimeSupplier;
import com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.event.DataManagementEvent;
import com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.state.DataManagementState;
import com.github.szilex94.edu.round_tracker.service.tracking.AmmunitionExpenseTrackingService;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Marks entries which are scheduled for archiving
 *
 * @author szilex94
 */
@Component
public final class MarkAction implements ArchivingAction {

    private final ArchivingTimeSupplier timeSupplier;

    private final AmmunitionExpenseTrackingService trackingService;

    public MarkAction(ArchivingTimeSupplier timeSupplier, AmmunitionExpenseTrackingService trackingService) {
        this.timeSupplier = timeSupplier;
        this.trackingService = trackingService;
    }

    @Override
    public Mono<Void> apply(StateContext<DataManagementState, DataManagementEvent> dataManagementStateDataManagementEventStateContext) {
        var cutoff = timeSupplier.get();
        return trackingService.markEntriesForArchiving(cutoff);
    }
}
