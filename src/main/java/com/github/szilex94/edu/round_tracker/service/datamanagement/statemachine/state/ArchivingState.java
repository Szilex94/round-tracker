package com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.state;

/**
 * Represents the states used in the archiving process
 *
 * @author szilex94
 */
public enum ArchivingState implements DataManagementState {
    /**
     * In this stage entries which are older than the cut-off date are marked for archiving
     */
    MARK_ENTITIES,
    /**
     * This stage represents the transfer of entities which where marked for archiving into the long term storage space
     */
    TRANSFER,
    /**
     * This stage represents the cleanup process where any leftover information is removed
     */
    CLEANUP
}
