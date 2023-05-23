package com.github.szilex94.edu.round_tracker.service.datamanagement.archiving;

import reactor.core.publisher.Mono;

/**
 * Offers a convenient way to interact with the underlying state machine which is in control of the archiving process
 *
 * @author szilex94
 */
public interface ArchivingService {

    Mono<ArchivingSubmissionRequest> triggerArchivingProcess();

}
