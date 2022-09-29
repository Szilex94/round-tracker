package com.github.szilex94.edu.round_tracker.service.tracking.summary;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class TrackingSummary {

    private final Map<String, Integer> summaryEntries;

    TrackingSummary(TrackingSummaryBuilder builder) {
        summaryEntries = builder.summaryEntries.build();

    }

    public static TrackingSummaryBuilder builder() {
        return new TrackingSummaryBuilder();
    }

    public static class TrackingSummaryBuilder {
        final ImmutableMap.Builder<String, Integer> summaryEntries = ImmutableMap.builder();

        public TrackingSummary build() {
            return new TrackingSummary(this);
        }
    }

}
