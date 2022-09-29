package com.github.szilex94.edu.round_tracker.service.tracking.summary;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

@Service
public class SummaryServiceImpl implements SummaryService {


    @Override
    public Mono<TrackingSummary> summaryForUser(String userId) {
        checkArgument(!isNullOrEmpty(userId), "Null or empty userId not allowed!");
        return Mono.just(DEFAULT_VALUE);
    }
}
