package com.github.szilex94.edu.round_tracker.rest.tracking.model;

import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionType;

import java.util.Map;

public record UserAmmunitionSummaryDto(
        String userId,
        Map<AmmunitionType,Integer> typeToCount
) {
}
