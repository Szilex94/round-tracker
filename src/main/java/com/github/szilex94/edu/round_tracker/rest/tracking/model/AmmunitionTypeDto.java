package com.github.szilex94.edu.round_tracker.rest.tracking.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum AmmunitionTypeDto {
    NINE_MILLIMETER("9_mm"),
    UNKNOWN("UNKNOWN");

    private static final Map<String, AmmunitionTypeDto> SIMPLE_NAME_TO_MEMBER = Arrays.stream(AmmunitionTypeDto.values())
            .collect(Collectors.toMap(AmmunitionTypeDto::getSimpleName, Function.identity()));

    private static final Map<String, AmmunitionTypeDto> NAME_TO_MEMBER = Arrays.stream(AmmunitionTypeDto.values())
            .collect(Collectors.toMap(Enum::name, Function.identity()));

    private final String simpleName;

    AmmunitionTypeDto(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getSimpleName() {
        return simpleName;
    }

    @JsonCreator
    public static AmmunitionTypeDto forValue(String value) {
        return SIMPLE_NAME_TO_MEMBER.getOrDefault(value, UNKNOWN);
    }

    public static AmmunitionTypeDto fromString(String name) {
        return NAME_TO_MEMBER.getOrDefault(name, UNKNOWN);
    }
}
