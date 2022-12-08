package com.github.szilex94.edu.round_tracker.rest.tracking.mapper;

import com.github.szilex94.edu.round_tracker.rest.tracking.model.AmmunitionChangeDto;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.UserAmmunitionSummaryDto;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import com.github.szilex94.edu.round_tracker.service.tracking.model.UserAmmunitionSummary;


public interface TrackingMapper {

    AmmunitionChange fromDto(String userId, AmmunitionChangeDto dto);

    UserAmmunitionSummaryDto toDto(UserAmmunitionSummary summary);

}