package com.github.szilex94.edu.round_tracker.rest.tracking.mapper;

import com.github.szilex94.edu.round_tracker.rest.tracking.model.AmmunitionChangeDto;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.AmmunitionSummaryDto;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChangeSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrackingMapper {

    @Mapping(target = "changeType", constant = "REGULAR")
    @Mapping(target = "recordedAt", expression = "java( java.time.OffsetDateTime.now() )")
    AmmunitionChange fromDto(String userId, AmmunitionChangeDto dto);

    AmmunitionSummaryDto toDto(AmmunitionChangeSummary summary);

}