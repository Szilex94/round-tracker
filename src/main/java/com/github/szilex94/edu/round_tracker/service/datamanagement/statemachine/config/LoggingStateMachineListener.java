package com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Slf4j
public final class LoggingStateMachineListener<S, E> extends StateMachineListenerAdapter<S, E> {
    @Override
    public void stateChanged(State<S, E> from, State<S, E> to) {
        //On Initial startup the from field is null
        var fromId = from == null ? "N/A" : from.getId();
        log.info("Recording state change: {} -> {}", fromId, to.getId());
    }

    @Override
    public void stateMachineError(StateMachine<S, E> stateMachine, Exception exception) {
        log.error("Encountered an error while in state: {}", stateMachine.getState(), exception);
    }
}
