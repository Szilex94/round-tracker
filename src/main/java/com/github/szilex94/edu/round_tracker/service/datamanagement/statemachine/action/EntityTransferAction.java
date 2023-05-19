package com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.action;

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
        //TODO circle back with logging
        return archivingService.transferMarkedEntities();
    }
}
