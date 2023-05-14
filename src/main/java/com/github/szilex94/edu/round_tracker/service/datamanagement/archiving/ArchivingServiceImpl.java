package com.github.szilex94.edu.round_tracker.service.datamanagement.archiving;

import com.github.szilex94.edu.round_tracker.service.datamanagement.fsm.event.ArchivingEvent;
import com.github.szilex94.edu.round_tracker.service.datamanagement.fsm.event.DataManagementEvent;
import com.github.szilex94.edu.round_tracker.service.datamanagement.fsm.state.DataManagementState;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ArchivingServiceImpl implements ArchivingService {

    private final StateMachine<DataManagementState, DataManagementEvent> fsm;

    public ArchivingServiceImpl(StateMachine<DataManagementState, DataManagementEvent> fsm) {
        this.fsm = fsm;
    }

    @Override
    public Mono<ArchivingSubmissionRequest> triggerArchivingProcess() {
        Mono<Message<DataManagementEvent>> message = Mono.just(ArchivingEvent.ARCHIVING_START)
                .map(GenericMessage::new);
        return fsm.sendEvent(message)
                .log()
                .then(Mono.just(new ArchivingSubmissionRequest()));
//                .map(this::handleEventSubmission);
    }

    private ArchivingSubmissionRequest handleEventSubmission(StateMachineEventResult<DataManagementState, DataManagementEvent> submissionResult) {
//TODO properly handle event submission
        return new ArchivingSubmissionRequest();
    }
}
