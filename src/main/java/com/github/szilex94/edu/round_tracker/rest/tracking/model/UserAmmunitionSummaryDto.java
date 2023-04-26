package com.github.szilex94.edu.round_tracker.rest.tracking.model;

import java.time.OffsetDateTime;
import java.util.Map;

public record UserAmmunitionSummaryDto(
        String userId,

        Map<String, SummaryEntryDto> codeToSummary
) {

    public record SummaryEntryDto(long grandTotal,
                                  OffsetDateTime lastChangeRecordedAt) {

    }
}
