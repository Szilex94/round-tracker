package com.github.szilex94.edu.round_tracker.service.datamanagement.fsm.state;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * Marker interface which groups all data management states
 *
 * @author szilex94
 */
public sealed interface DataManagementState permits GenericState, ArchivingState {
    static Set<DataManagementState> allStates() {
        return Support.ALL_STATES;
    }
}

class Support {
    static final Set<DataManagementState> ALL_STATES = ImmutableSet.<DataManagementState>builder()
            .add(GenericState.values())
            .add(ArchivingState.values())
            .build();

}
