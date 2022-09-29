package com.github.szilex94.edu.round_tracker.rest.tracking.summary;

import lombok.Data;

import java.util.Map;

@Data
public class TrackingSummaryDto {

    private Map<String, Integer> summaryEntries;
}
