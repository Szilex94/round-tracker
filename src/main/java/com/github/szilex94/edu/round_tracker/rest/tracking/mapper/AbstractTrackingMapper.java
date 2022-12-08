package com.github.szilex94.edu.round_tracker.rest.tracking.mapper;

import com.github.szilex94.edu.round_tracker.rest.tracking.model.AmmunitionChangeDto;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.AmmunitionTypeDto;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionType;
import com.google.common.collect.BiMap;
import com.google.common.collect.EnumBiMap;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import static com.github.szilex94.edu.round_tracker.rest.tracking.model.AmmunitionTypeDto.UNKNOWN;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Slf4j
public abstract class AbstractTrackingMapper implements TrackingMapper {

    private static final BiMap<AmmunitionType, AmmunitionTypeDto> internalToDto = init();

    private static BiMap<AmmunitionType, AmmunitionTypeDto> init() {
        EnumBiMap<AmmunitionType, AmmunitionTypeDto> result = EnumBiMap.create(AmmunitionType.class, AmmunitionTypeDto.class);
        for (AmmunitionType current : AmmunitionType.values()) {
            var target = AmmunitionTypeDto.fromString(current.name());
            if (target == UNKNOWN) {
                log.warn("No adequate mapping exists for internal AmmunitionType '{}'!", current);
            } else {
                result.put(current, target);
            }
        }
        return result;
    }

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "dto.amount", target = "amount")
    @Mapping(source = "dto.type", target = "changeType")
    @Mapping(source = "dto.ammunitionType", target = "ammunitionType")
    @Override
    public abstract AmmunitionChange fromDto(String userId, AmmunitionChangeDto dto);

    protected AmmunitionType externalAmmunitionTypeToInternal(AmmunitionTypeDto dto) {
        return internalToDto.inverse().get(dto);
    }
}
