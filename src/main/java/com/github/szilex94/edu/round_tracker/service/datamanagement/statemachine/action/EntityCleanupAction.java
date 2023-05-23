package com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.action;

import com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.event.DataFlowEvent;
import com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.state.DataManagementState;
import com.github.szilex94.edu.round_tracker.service.tracking.AmmunitionExpenseArchivingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class EntityCleanupAction implements ArchivingAction {

    private final AmmunitionExpenseArchivingService archivingService;

    public EntityCleanupAction(AmmunitionExpenseArchivingService archivingService) {
        this.archivingService = archivingService;
    }

    @Override
    public Mono<Void> apply(StateContext<DataManagementState, DataFlowEvent> dataManagementStateDataFlowEventStateContext) {
        return archivingService.removeArchivedEntities()
                .doOnNext(removedCount -> log.info("Successfully removed {} of archived entities from short term storage!"))
                .then();
    }
}
