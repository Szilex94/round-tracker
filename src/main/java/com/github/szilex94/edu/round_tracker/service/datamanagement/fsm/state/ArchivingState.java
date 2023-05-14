package com.github.szilex94.edu.round_tracker.service.datamanagement.fsm.state;

public enum ArchivingState implements DataManagementState {
    MARK_ENTITIES,
    TRANSFER,
    CLEANUP,
    SANITY_CHECK
}
