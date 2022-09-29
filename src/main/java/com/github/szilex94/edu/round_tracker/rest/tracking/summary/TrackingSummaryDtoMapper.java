package com.github.szilex94.edu.round_tracker.rest.tracking.summary;

import com.github.szilex94.edu.round_tracker.service.tracking.summary.TrackingSummary;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrackingSummaryDtoMapper {

    TrackingSummaryDto toDto(TrackingSummary trackingSummary);
}
