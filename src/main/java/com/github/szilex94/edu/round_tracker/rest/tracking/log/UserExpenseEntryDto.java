package com.github.szilex94.edu.round_tracker.rest.tracking.log;

import com.github.szilex94.edu.round_tracker.rest.tracking.CaliberDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.OffsetDateTime;

@Data
public class UserExpenseEntryDto {

    private String id;

    @Positive
    private int amount;

    @NotNull(message = "Null log type not allowed!")
    private LogTypeDto type;

    @NotNull
    private CaliberDto caliber;

    private OffsetDateTime timeStamp;

}
