package com.github.szilex94.edu.round_tracker.rest.support.caliber;

import com.github.szilex94.edu.round_tracker.rest.jakarta.OnCreate;
import com.github.szilex94.edu.round_tracker.rest.jakarta.OnUpdate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CaliberTypeDefinitionDto(
        @NotEmpty(message = "Null or empty code not allowed!", groups = {OnCreate.class, OnUpdate.class})
        @Size(max = 10, message = "Code size cannot exceed 10 characters", groups = OnCreate.class)
        @Pattern(regexp = "^(?:[a-zA-Z\\_0-9])+$", message = "Only non white space characters allowed!", groups = OnCreate.class)
        String code,
        String displayName,
        String description
) {
}
