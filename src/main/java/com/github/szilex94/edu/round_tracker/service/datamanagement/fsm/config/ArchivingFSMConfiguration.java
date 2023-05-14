package com.github.szilex94.edu.round_tracker.service.datamanagement.fsm.config;

import com.github.szilex94.edu.round_tracker.service.datamanagement.fsm.event.ArchivingEvent;
import com.github.szilex94.edu.round_tracker.service.datamanagement.fsm.event.DataManagementEvent;
import com.github.szilex94.edu.round_tracker.service.datamanagement.fsm.state.ArchivingState;
import com.github.szilex94.edu.round_tracker.service.datamanagement.fsm.state.DataManagementState;
import com.github.szilex94.edu.round_tracker.service.datamanagement.fsm.state.GenericState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
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
                .states(DataManagementState.allStates());
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<DataManagementState, DataManagementEvent> transitions)
            throws Exception {
        //TODO finish configuration
        transitions
                .withExternal()
                .source(GenericState.IDLE).target(ArchivingState.MARK_ENTITIES).event(ArchivingEvent.ARCHIVING_START);
//                .and()
//                .withInternal()
//                .source(States.S1).target(States.S2).event(Events.E2);
    }

}
