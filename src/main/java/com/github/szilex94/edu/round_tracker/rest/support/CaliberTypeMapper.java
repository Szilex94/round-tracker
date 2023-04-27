package com.github.szilex94.edu.round_tracker.rest.support;

import com.github.szilex94.edu.round_tracker.service.support.caliber.CaliberTypeDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CaliberTypeMapper {

    CaliberTypeDefinition fromDto(CaliberTypeDefinitionDto dto);

    CaliberTypeDefinitionDto toDto(CaliberTypeDefinition def);

}
