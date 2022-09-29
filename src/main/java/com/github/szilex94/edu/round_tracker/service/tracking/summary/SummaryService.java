package com.github.szilex94.edu.round_tracker.service.tracking.summary;

import reactor.core.publisher.Mono;

public interface SummaryService {

    TrackingSummary DEFAULT_VALUE = TrackingSummary.builder()
            .build();

    Mono<TrackingSummary> summaryForUser(String userId);
}
