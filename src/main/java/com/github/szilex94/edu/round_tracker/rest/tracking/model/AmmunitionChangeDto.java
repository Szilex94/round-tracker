package com.github.szilex94.edu.round_tracker.rest.tracking.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AmmunitionChangeDto(String id,
                                  @Positive
                                  int amount,
                                  @NotNull(message = "Null log type not allowed!")
                                  ChangeTypeDto type,
                                  @NotNull
                                  AmmunitionTypeDto ammunitionType) {
}