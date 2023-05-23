package com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.action;

import com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.event.DataFlowEvent;
import com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.state.DataManagementState;
import org.springframework.statemachine.action.ReactiveAction;

/**
 * Generic interface for grouping all action implementations revolving around the archiving process
 *
 * @author szilex94
 */
public interface ArchivingAction extends ReactiveAction<DataManagementState, DataFlowEvent> {

}
