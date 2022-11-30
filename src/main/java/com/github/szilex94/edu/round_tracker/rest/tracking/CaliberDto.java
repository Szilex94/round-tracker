package com.github.szilex94.edu.round_tracker.rest.tracking;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum CaliberDto {
    NINE_MILLIMETER("9_MM"),
    UNKNOWN("UNKNOWN");

    private static final Map<String, CaliberDto> SIMPLE_NAME_TO_MEMBER = Arrays.stream(CaliberDto.values())
            .collect(Collectors.toMap(CaliberDto::getSimpleName, Function.identity()));

    private final String simpleName;

    CaliberDto(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getSimpleName() {
        return simpleName;
    }

    @JsonCreator
    public static CaliberDto forSimpleName(String simpleName) {
        return SIMPLE_NAME_TO_MEMBER.getOrDefault(simpleName, UNKNOWN);
    }
}
