package com.github.szilex94.edu.round_tracker.rest.tracking.model;

import java.util.Map;

public record UserAmmunitionSummaryDto(
        String userId,
        Map<AmmunitionTypeDto, Integer> typeToCount
) {
}
