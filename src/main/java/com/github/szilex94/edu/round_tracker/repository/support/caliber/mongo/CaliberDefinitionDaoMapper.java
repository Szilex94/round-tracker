package com.github.szilex94.edu.round_tracker.repository.support.caliber.mongo;

import com.github.szilex94.edu.round_tracker.service.support.caliber.CaliberTypeDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CaliberDefinitionDaoMapper {

    CaliberTypeDefinition fromDao(CaliberTypeDefinitionDao dao);

    CaliberTypeDefinitionDao toDao(CaliberTypeDefinition user);
}
