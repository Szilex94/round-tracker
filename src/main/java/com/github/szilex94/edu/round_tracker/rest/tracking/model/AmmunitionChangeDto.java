package com.github.szilex94.edu.round_tracker.rest.tracking.model;

import com.github.szilex94.edu.round_tracker.rest.jakarta.annotation.not_zero.NotZero;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AmmunitionChangeDto(String id,
                                  @NotNull
                                  @NotEmpty
                                  String ammunitionCode,
                                  @NotZero
                                  int amount) {
}