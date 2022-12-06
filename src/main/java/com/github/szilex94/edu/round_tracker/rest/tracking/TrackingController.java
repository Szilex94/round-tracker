package com.github.szilex94.edu.round_tracker.rest.tracking;

import com.github.szilex94.edu.round_tracker.rest.tracking.model.CaliberDto;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.LogTypeDto;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.UserExpenseEntryDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("round-tracker/v1/tracking")
public class TrackingController {

    @PostMapping(path = "user/{userId}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    //TODO define a summary?
    public Mono<UserExpenseEntryDto> trackChange(@PathVariable String userId,
                                                 @Valid @RequestBody UserExpenseEntryDto expenseEntryDto) {
        System.err.println(expenseEntryDto);
        //TODO continue from here
        return Mono.just(new UserExpenseEntryDto(null,
                10,
                LogTypeDto.EXPENSE,
                CaliberDto.NINE_MILLIMETER,
                OffsetDateTime.MAX));
    }
}
