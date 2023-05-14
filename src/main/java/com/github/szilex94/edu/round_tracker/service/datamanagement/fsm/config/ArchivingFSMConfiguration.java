package com.github.szilex94.edu.round_tracker.service.datamanagement.fsm.config;

import com.github.szilex94.edu.round_tracker.service.datamanagement.fsm.event.ArchivingEvent;
import com.github.szilex94.edu.round_tracker.service.datamanagement.fsm.event.DataManagementEvent;
import com.github.szilex94.edu.round_tracker.service.datamanagement.fsm.state.ArchivingState;
import com.github.szilex94.edu.round_tracker.service.datamanagement.fsm.state.DataManagementState;
import com.github.szilex94.edu.round_tracker.service.datamanagement.fsm.state.GenericState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachine(contextEvents = false)
@Slf4j
public class ArchivingFSMConfiguration extends StateMachineConfigurerAdapter<DataManagementState, DataManagementEvent> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<DataManagementState, DataManagementEvent> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(new LoggingStateMachineListener<>());
    }

    @Override
    public void configure(StateMachineStateConfigurer<DataManagementState, DataManagementEvent> states)
            throws Exception {

        states.withStates()
                .initial(GenericState.IDLE)
                .stateDo(ArchivingState.MARK_ENTITIES, new SimpleAction("MARK"))
                .stateDo(ArchivingState.TRANSFER, new SimpleAction("TRANSFER"))
                .stateDo(ArchivingState.CLEANUP, new SimpleAction("CLEANUP"))
                .states(DataManagementState.allStates());
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<DataManagementState, DataManagementEvent> transitions)
            throws Exception {

        transitions
                .withExternal()
                .source(GenericState.IDLE).target(ArchivingState.MARK_ENTITIES).event(ArchivingEvent.ARCHIVING_START)
                .and()
                .withExternal()
                .source(ArchivingState.MARK_ENTITIES).target(ArchivingState.TRANSFER)
                .and()
                .withExternal()
                .source(ArchivingState.TRANSFER).target(ArchivingState.CLEANUP)
                .and()
                .withExternal()
                .source(ArchivingState.CLEANUP).target(GenericState.IDLE);
    }

    @AllArgsConstructor
    static class SimpleAction implements Action<DataManagementState, DataManagementEvent> {

        private String actionName;


        @Override
        public void execute(StateContext<DataManagementState, DataManagementEvent> context) {
            log.info("Simple Action '{}' performed", actionName);
        }
    }

}
