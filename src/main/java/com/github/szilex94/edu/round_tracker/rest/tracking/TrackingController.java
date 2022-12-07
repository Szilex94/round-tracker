package com.github.szilex94.edu.round_tracker.rest.tracking;

import com.github.szilex94.edu.round_tracker.rest.tracking.model.TrackingSummaryDto;
import com.github.szilex94.edu.round_tracker.rest.tracking.model.AmmunitionChangeDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("round-tracker/v1/tracking")
@Slf4j
public class TrackingController {

    @PostMapping(path = "user/{userId}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Mono<TrackingSummaryDto> trackChange(@PathVariable String userId,
                                                @Valid @RequestBody AmmunitionChangeDto expenseEntryDto) {
        log.debug("{}", expenseEntryDto);
        //TODO continue from here
        return Mono.just(new TrackingSummaryDto(10));
    }
}
