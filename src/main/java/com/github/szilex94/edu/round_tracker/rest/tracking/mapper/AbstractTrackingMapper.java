package com.github.szilex94.edu.round_tracker.rest.tracking.mapper;

import com.github.szilex94.edu.round_tracker.rest.tracking.model.AmmunitionChangeDto;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.AmmunitionTypeDto;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionChange;
import com.github.szilex94.edu.round_tracker.service.tracking.model.AmmunitionType;
import com.google.common.collect.BiMap;
import com.google.common.collect.EnumBiMap;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Map;

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

    protected Map<AmmunitionTypeDto, Integer> convertAmmunitionSummary(Map<AmmunitionType, Integer> in) {
        ImmutableMap.Builder<AmmunitionTypeDto, Integer> builder = ImmutableMap.builder();

        for (var entry : in.entrySet()) {
            builder.put(internalToDto.get(entry.getKey()), entry.getValue());
        }
        return builder.build();
    }
}
