package com.github.szilex94.edu.round_tracker.service.datamanagement.archiving;

import com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.event.DataFlowEvent;
import com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.state.DataManagementState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ArchivingServiceImpl implements ArchivingService {

    private final StateMachine<DataManagementState, DataFlowEvent> fsm;

    public ArchivingServiceImpl(StateMachine<DataManagementState, DataFlowEvent> fsm) {
        this.fsm = fsm;
    }

    @Override
    public Mono<ArchivingSubmissionRequest> triggerArchivingProcess() {
        Mono<Message<DataFlowEvent>> message = Mono.just(DataFlowEvent.ARCHIVING_START)
                .map(GenericMessage::new);
        return fsm.sendEvent(message)
                .singleOrEmpty()
                .map(this::handleEventSubmission);
    }

    private ArchivingSubmissionRequest handleEventSubmission(StateMachineEventResult<DataManagementState, DataFlowEvent> submissionResult) {
        var currentState = submissionResult.getRegion().getState().getId();
        var type = submissionResult.getResultType();
        switch (type) {
            case ACCEPTED:
            case DEFERRED:
                return new ArchivingSubmissionRequest(true);
            case DENIED:
                log.debug("Archiving process start denied! Currently processing state {}", currentState);
                return new ArchivingSubmissionRequest(false, "Currently processing  state: " + currentState);
            default:
                throw new IllegalStateException("Unknown state machine result type: " + type);
        }
    }
}
