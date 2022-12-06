package com.github.szilex94.edu.round_tracker.rest.tracking.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;

public record UserExpenseEntryDto(String id,
                                  @Positive
                                  int amount,
                                  @NotNull(message = "Null log type not allowed!")
                                  LogTypeDto type,
                                  @NotNull
                                  CaliberDto caliber,
                                  OffsetDateTime timeStamp) {
}