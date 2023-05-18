package com.github.szilex94.edu.round_tracker.service.datamanagement.statemachine.event;

/**
 * Marker interface which groups all data management events
 *
 * @author szilex94
 */
public sealed interface DataManagementEvent permits ArchivingEvent {
    //Marker interface
}
