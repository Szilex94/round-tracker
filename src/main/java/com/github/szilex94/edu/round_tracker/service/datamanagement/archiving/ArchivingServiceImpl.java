package com.github.szilex94.edu.round_tracker.service.datamanagement.archiving;

import com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.event.DataFlowEvent;
import com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.state.DataManagementState;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
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
                .then(Mono.just(new ArchivingSubmissionRequest()));
//                .map(this::handleEventSubmission);
    }

    private ArchivingSubmissionRequest handleEventSubmission(StateMachineEventResult<DataManagementState, DataFlowEvent> submissionResult) {
//TODO properly handle event submission
        return new ArchivingSubmissionRequest();
    }
}
